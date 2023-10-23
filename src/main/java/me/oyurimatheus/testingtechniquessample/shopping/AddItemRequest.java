package me.oyurimatheus.testingtechniquessample.shopping;

import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AddItemRequest {

    @Positive
    private Long productId;

    @Positive
    private int quantity;


    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
