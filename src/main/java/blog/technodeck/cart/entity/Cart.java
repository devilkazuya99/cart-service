package blog.technodeck.cart.entity;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Cart extends PanacheEntity {

    public Long userId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<CartItem> items;
    public ZonedDateTime createdDate;

}
