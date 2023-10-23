package me.oyurimatheus.testingtechniquessample.shopping;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v1/cart")
public class ShoppingController {

    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;

    public ShoppingController(ShoppingCartRepository cartRepository,
                              ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartResponse> showCart(@PathVariable("id") Long id) {

        return cartRepository.findById(id)
                .map(cart -> ResponseEntity.ok(new ShoppingCartResponse(cart)))
                .orElse(ResponseEntity.ok().build());

    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartResponse> addItem(@RequestBody AddItemRequest request) {

        var product = productRepository.findById(request.getProductId())
                .orElseThrow(IllegalArgumentException::new);

        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.addItem(product, request.getQuantity());

        cartRepository.save(shoppingCart);
        return ResponseEntity.ok(new ShoppingCartResponse(shoppingCart));

    }

    @PostMapping("/{id}/add")
    public ResponseEntity<ShoppingCartResponse> addItem(@PathVariable("id") Long id,
                                                        @RequestBody AddItemRequest request) {

        var shoppingCart = cartRepository.findById(id)
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));


        var product = productRepository.findById(request.getProductId())
                                        .orElseThrow(IllegalArgumentException::new);

        shoppingCart.addItem(product, request.getQuantity());

        cartRepository.save(shoppingCart);
        return ResponseEntity.ok(new ShoppingCartResponse(shoppingCart));

    }


}
