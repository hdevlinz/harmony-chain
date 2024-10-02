package com.tth.commonlibrary.service;

import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import com.tth.commonlibrary.entity.Cart;

import java.util.Map;

public interface CartService {

    Cart getCart();

    void addProductToCart(Cart cart, ProductRequestAddToCart request);

    void updateProductInCart(Cart cart, String productId, Map<String, String> request);

    void deleteProductFromCart(Cart cart, String productId);

    void clearCart(Cart cart);

    Map<String, CartItemResponse> getCartResponse(Cart cart);

}
