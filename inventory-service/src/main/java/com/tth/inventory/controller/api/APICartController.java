package com.tth.inventory.controller.api;

import com.tth.order.dto.APIResponse;
import com.tth.order.dto.request.product.ProductRequestAddToCart;
import com.tth.order.dto.response.cart.CartDetailsResponse;
import com.tth.order.entity.Cart;
import com.tth.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping(path = "/carts", produces = "application/json; charset=UTF-8")
public class APICartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart() {
        Cart cart = this.cartService.findCart();

        return ResponseEntity.ok(APIResponse.<Map<String, CartDetailsResponse>>builder().result(this.cartService.getCartResponse(cart)));
    }

    @PostMapping(path = "/products")
    public ResponseEntity<?> addProductToCart(@RequestBody ProductRequestAddToCart productRequestAddToCart) {
        Cart cart = this.cartService.findCart();
        this.cartService.addProductToCart(cart, productRequestAddToCart);

        return ResponseEntity.ok(APIResponse.<Map<String, CartDetailsResponse>>builder().result(this.cartService.getCartResponse(cart)));
    }

    @PatchMapping(path = "/products/{productId}")
    public ResponseEntity<?> updateProductInCart(@PathVariable(value = "productId") String productId, @RequestBody Map<String, String> request) {
        Cart cart = this.cartService.findCart();
        this.cartService.updateProductInCart(cart, productId, request);

        return ResponseEntity.ok(APIResponse.<Map<String, CartDetailsResponse>>builder().result(this.cartService.getCartResponse(cart)));
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable(value = "productId") String productId) {
        Cart cart = this.cartService.findCart();
        this.cartService.deleteProductFromCart(cart, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/products/clear")
    public ResponseEntity<?> clearCart() {
        this.cartService.clearCart(this.cartService.findCart());

        return ResponseEntity.noContent().build();
    }

}
