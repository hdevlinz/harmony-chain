package com.tth.cart.mapper;

import com.tth.cart.entity.CartItem;
import com.tth.cart.mapper.helper.CartMappingHelper;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CartMappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CartMapper {

    @Named("toCartItemResponse")
    @Mapping(target = "product", source = "productId", qualifiedByName = "getProduct")
    CartItemResponse toCartItemResponse(CartItem cartItem);

}
