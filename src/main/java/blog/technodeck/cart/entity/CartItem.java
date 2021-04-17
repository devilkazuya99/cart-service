package blog.technodeck.cart.entity;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class CartItem extends PanacheEntity {

    public Long productId;
    public String name;
    public Float price;
    public int quantity;
    
    
    @Override
    public String toString() {
        return "CartItem [id=" + id + ", productId=" + productId + ", name=" + name + ", price=" + price + ", quantity=" + quantity
                + "]";
    }

}
