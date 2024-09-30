package com.tth.cart.controller;

import com.tth.cart.entity.Cart;
import com.tth.cart.service.CartService;
import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping(produces = "application/json; charset=UTF-8")
public class APICartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart() {
        Map<String, CartItemResponse> cartResponse = this.cartService.getCartResponse(this.cartService.getCart());

        return ResponseEntity.ok(APIResponse.<Map<String, CartItemResponse>>builder().result(cartResponse).build());
    }

    @PostMapping(path = "/products")
    public ResponseEntity<?> addProductToCart(@RequestBody ProductRequestAddToCart productRequestAddToCart) {
        Cart cart = this.cartService.getCart();
        this.cartService.addProductToCart(cart, productRequestAddToCart);
        Map<String, CartItemResponse> cartResponse = this.cartService.getCartResponse(cart);

        return ResponseEntity.ok(APIResponse.<Map<String, CartItemResponse>>builder().result(cartResponse).build());
    }

    @PatchMapping(path = "/products/{productId}")
    public ResponseEntity<?> updateProductInCart(@PathVariable(value = "productId") String productId, @RequestBody Map<String, String> request) {
        Cart cart = this.cartService.getCart();
        this.cartService.updateProductInCart(cart, productId, request);
        Map<String, CartItemResponse> cartResponse = this.cartService.getCartResponse(cart);

        return ResponseEntity.ok(APIResponse.<Map<String, CartItemResponse>>builder().result(cartResponse).build());
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable(value = "productId") String productId) {
        Cart cart = this.cartService.getCart();
        this.cartService.deleteProductFromCart(cart, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/products/clear")
    public ResponseEntity<?> clearCart() {
        this.cartService.clearCart(this.cartService.getCart());

        return ResponseEntity.noContent().build();
    }

}
