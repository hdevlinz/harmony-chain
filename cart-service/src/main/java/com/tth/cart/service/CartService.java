package com.tth.cart.service;

import com.tth.cart.entity.Cart;
import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addProductToCart(Cart cart, ProductRequestAddToCart request);

    void updateProductInCart(Cart cart, String productId, Map<String, String> request);

    void deleteProductFromCart(Cart cart, String productId);

    void clearCart(Cart cart);

    Map<String, CartItemResponse> getCartResponse(Cart cart);

}
