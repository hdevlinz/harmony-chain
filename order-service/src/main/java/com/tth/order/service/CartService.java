package com.tth.order.service;

import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartDetailsResponse;
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
