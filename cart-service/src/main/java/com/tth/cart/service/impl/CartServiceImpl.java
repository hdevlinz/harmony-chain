package com.tth.cart.service.impl;

import com.tth.cart.entity.Cart;
import com.tth.cart.entity.CartItem;
import com.tth.cart.mapper.CartMapper;
import com.tth.cart.repository.CartItemRepository;
import com.tth.cart.repository.CartRepository;
import com.tth.cart.repository.httpclient.ProductClient;
import com.tth.cart.service.CartService;
import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.commonlibrary.utils.ConverterUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final ProductClient productClient;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        return this.cartRepository.findByUserId(user.getName()).orElseGet(() -> {
            Cart newCart = Cart.builder()
                    .userId(user.getName())
                    .cartItems(new HashSet<>())
                    .build();
            this.cartRepository.save(newCart);
            return newCart;
        });
    }

    @Override
    public void addProductToCart(Cart cart, ProductRequestAddToCart request) {
        ProductDetailsResponse product = this.productClient.getProduct(request.getProductId()).getResult();

        CartItem cartItem = Optional.ofNullable(cart.getCartItems()).orElseGet(HashSet::new).stream()
                .filter(cd -> cd.getProductId().equals(product.getId())).findFirst()
                .orElseGet(() -> CartItem.builder()
                        .cartId(cart.getId())
                        .productId(product.getId())
                        .unitPrice(product.getPrice())
                        .quantity(0F)
                        .build()
                );
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        this.cartItemRepository.save(cartItem);

        cart.addItem(cartItem);
        this.cartRepository.save(cart);
    }

    @Override
    public void updateProductInCart(Cart cart, String productId, Map<String, String> request) {
        Set<CartItem> cartItems = Optional.ofNullable(cart.getCartItems()).orElseGet(HashSet::new);
        CartItem cartItem = cartItems.stream()
                .filter(cd -> cd.getProductId().equals(productId)).findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS_IN_CART));

        request.forEach((key, value) -> {
            try {
                Field cartItemField = CartItem.class.getDeclaredField(key);
                cartItemField.setAccessible(true);

                Object convertedValue = ConverterUtils.convertValue(cartItemField.getType(), value);
                cartItemField.set(cartItem, convertedValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        });
        this.cartItemRepository.save(cartItem);

        cart.setCartItems(cartItems);
        this.cartRepository.save(cart);
    }

    @Override
    public void deleteProductFromCart(Cart cart, String productId) {
        CartItem cartItem = Optional.ofNullable(cart.getCartItems()).orElseGet(HashSet::new).stream()
                .filter(cd -> cd.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS_IN_CART));
        this.cartItemRepository.delete(cartItem);

        cart.removeItem(cartItem);
        this.cartRepository.save(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        this.cartItemRepository.deleteAll(cart.getCartItems());
        cart.clearItems();
        this.cartRepository.save(cart);
    }

    @Override
    public Map<String, CartItemResponse> getCartResponse(Cart cart) {
        return Optional.ofNullable(cart.getCartItems()).orElseGet(HashSet::new).stream()
                .collect(Collectors.toMap(CartItem::getProductId, this.cartMapper::toCartItemResponse));
    }

}
