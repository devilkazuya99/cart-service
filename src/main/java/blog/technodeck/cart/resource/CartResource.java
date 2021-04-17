package blog.technodeck.cart.resource;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blog.technodeck.cart.entity.Cart;
import blog.technodeck.cart.entity.CartItem;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    Logger logger = LoggerFactory.getLogger(getClass());

    @GET
    public Cart getCart(@HeaderParam("X-User-Id") Long userId) {
        Cart cart = findCart(userId);
        return cart;
    }
    
    @Transactional
    @POST
    public Cart addItem(@HeaderParam("X-User-Id") Long userId, CartItem item) {
        logger.info("User ID = {}", userId);
        logger.info("{}", item);
        Cart cart = findCart(userId);
        cart.items.add(item);
        cart.persist();
        return cart;
    }

    @Transactional
    @PUT
    public Cart updateItem(@HeaderParam("X-User-Id") Long userId, CartItem item) {
        logger.info("User ID = {}", userId);
        logger.info("{}", item);
        Cart cart = findCart(userId);
        cart.items.forEach(i -> {
            if(i.id == item.id) {
                // only allow quantity change
                i.quantity = item.quantity;
                i.persist();
            }
        });
        return cart;
    }

    @Transactional
    @DELETE
    public Cart removeItem(@HeaderParam("X-User-Id") Long userId, CartItem item) {
        logger.info("User ID = {}", userId);
        logger.info("{}", item);
        Cart cart = findCart(userId);
        cart.items.removeIf(ci -> ci.id.equals(item.id));
        cart.persist();
        return cart;
    }

    private Cart findCart(Long userId) {
        Cart cart = (Cart) Cart.find("userId", userId).firstResultOptional().orElse(new Cart());
        
        logger.info("Cart.id = {}", cart.id);
        if (cart.id == null) {
            cart.createdDate = ZonedDateTime.now(ZoneId.of("UTC"));
            cart.items = new ArrayList<>();
            cart.userId = userId;
        }
        return cart;
    }
}
