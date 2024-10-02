package com.tth.commonlibrary.mapper;

import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import com.tth.commonlibrary.entity.CartItem;
import com.tth.commonlibrary.mapper.helper.CartMappingHelper;
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
