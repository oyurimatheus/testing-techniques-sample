package me.oyurimatheus.testingtechniquessample.shopping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ShoppingCartResponse {


    private final Long id;
    private final DiscountResponse discountResponse;
    private final List<CartItemsResponse> items;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final BigDecimal subtotal;
    private final BigDecimal total;

    public ShoppingCartResponse(ShoppingCart cart) {
        this.id = cart.getId();
        this.discountResponse = new DiscountResponse(cart.getCoupon());
        this.items = cart.getItems().stream().map(CartItemsResponse::new).toList();
        this.subtotal = cart.subtotal();
        this.total = cart.total();
        this.createdAt = cart.getCreatedAt();
        this.updatedAt = cart.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public DiscountResponse getDiscountResponse() {
        return discountResponse;
    }

    public List<CartItemsResponse> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private static class DiscountResponse {

        private final String name;
        private final String code;
        private final BigDecimal discountValue;

        public DiscountResponse(DiscountCoupon coupon) {
            this.name = coupon.getName();
            this.code = coupon.getCode();
            this.discountValue = coupon.getDiscountValue();
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public BigDecimal getDiscountValue() {
            return discountValue;
        }
    }

    private static class CartItemsResponse {

        private final ProductResponse product;
        private final int quantity;
        private final BigDecimal total;
        private final LocalDateTime addedAt;

        public CartItemsResponse(CartItem item) {

            this.product = new ProductResponse(item.getProduct());
            this.quantity = item.getQuantity();
            this.total = item.total();
            this.addedAt = item.getCreatedAt();
        }

        public ProductResponse getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public LocalDateTime getAddedAt() {
            return addedAt;
        }

        public static class ProductResponse {

            private final BigDecimal price;
            private final Long id;
            private final String name;

            public ProductResponse(Product product) {
                this.id = product.getId();
                this.name = product.getName();
                this.price = product.getPrice();
            }
        }
    }
}
