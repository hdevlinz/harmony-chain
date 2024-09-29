package com.tth.inventory.service;

import com.tth.order.dto.request.product.ProductRequestAddToCart;
import com.tth.order.dto.response.cart.CartDetailsResponse;
import com.tth.order.entity.Cart;

import java.util.Map;

public interface CartService {

    Cart findCart();

    void addProductToCart(Cart cart, ProductRequestAddToCart productRequestAddToCart);

    void updateProductInCart(Cart cart, String productId, Map<String, String> request);

    void deleteProductFromCart(Cart cart, String productId);

    void clearCart(Cart cart);

    Map<String, CartDetailsResponse> getCartResponse(Cart cart);

}
