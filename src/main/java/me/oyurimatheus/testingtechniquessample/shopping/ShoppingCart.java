package me.oyurimatheus.testingtechniquessample.shopping;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.PERSIST;

@Table(name = "shopping_carts")
@Entity
public class ShoppingCart {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private DiscountCoupon coupon;

    @OneToMany(mappedBy = "cart", cascade = { PERSIST })
    @NotEmpty
    private List<CartItem> items;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public void addItem(Product product, int quantity) {

        this.items.add(new CartItem(this, product, quantity));
    }

    public BigDecimal subtotal() {
        return items.stream()
                    .map(CartItem::total)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
    }


    public BigDecimal total() {
        var subtotal = this.subtotal();

        return coupon.apply(subtotal);

    }

    public Long getId() {
        return id;
    }

    public DiscountCoupon getCoupon() {
        return coupon;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
