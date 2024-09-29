package com.tth.order.service.impl;

import com.tth.commonlibrary.dto.request.product.ProductRequestAddToCart;
import com.tth.commonlibrary.dto.response.cart.CartDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.commonlibrary.util.Utils;
import com.tth.order.entity.Cart;
import com.tth.order.entity.CartDetails;
import com.tth.order.mapper.CartMapper;
import com.tth.order.repository.CartDetailsRepository;
import com.tth.order.repository.CartRepository;
import com.tth.order.repository.httpclient.ProductClient;
import com.tth.order.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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
    private final CartDetailsRepository cartDetailsRepository;

    @Override
    public Cart findCart() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        return this.cartRepository.findByUserId(user.getName()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(user.getName());
            this.cartRepository.save(newCart);
            return newCart;
        });
    }

    @Override
    public void addProductToCart(Cart cart, ProductRequestAddToCart productRequestAddToCart) {
        ProductDetailsResponse product = this.productClient.getProduct(productRequestAddToCart.getProductId()).getResult();
        CartDetails existingCartDetails = Optional.ofNullable(cart.getCartDetails()).orElseGet(Set::of).stream()
                .filter(cd -> cd.getProductId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingCartDetails != null) {
            existingCartDetails.setQuantity(existingCartDetails.getQuantity() + productRequestAddToCart.getQuantity());
            this.cartDetailsRepository.save(existingCartDetails);
        } else {
            CartDetails cartDetails = CartDetails.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .unitPrice(product.getPrice())
                    .quantity(productRequestAddToCart.getQuantity())
                    .build();
            this.cartDetailsRepository.save(cartDetails);
        }
    }

    @Override
    public void updateProductInCart(Cart cart, String productId, Map<String, String> request) {
        CartDetails cartDetails = Optional.ofNullable(cart.getCartDetails()).orElseGet(Set::of).parallelStream()
                .filter(cd -> cd.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS_IN_CART));

        request.forEach((key, value) -> {
            try {
                Field cartDetailsField = CartDetails.class.getDeclaredField(key);
                cartDetailsField.setAccessible(true);

                Object convertedValue = Utils.convertValue(cartDetailsField.getType(), value);
                cartDetailsField.set(cartDetails, convertedValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        });

        this.cartDetailsRepository.save(cartDetails);
    }

    @Override
    public void deleteProductFromCart(Cart cart, String productId) {
        CartDetails cartDetails = Optional.ofNullable(cart.getCartDetails()).orElseGet(Set::of).parallelStream()
                .filter(cd -> cd.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTS_IN_CART));

        this.cartDetailsRepository.delete(cartDetails);
    }

    @Override
    public void clearCart(Cart cart) {
        this.cartDetailsRepository.deleteAll(cart.getCartDetails());
    }

    @Override
    public Map<String, CartDetailsResponse> getCartResponse(Cart cart) {
        return Optional.ofNullable(cart.getCartDetails()).orElseGet(Set::of).stream()
                .collect(Collectors.toMap(CartDetails::getProductId, this.cartMapper::toCartDetailsResponse));
    }

}
