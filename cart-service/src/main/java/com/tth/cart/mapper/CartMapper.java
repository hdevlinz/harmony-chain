package com.tth.cart.mapper;

import com.tth.cart.entity.CartItem;
import com.tth.cart.mapper.helper.CartMappingHelper;
import com.tth.commonlibrary.dto.response.cart.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CartMappingHelper.class})
public interface CartMapper {

    @Named("toCartItemResponse")
    @Mapping(target = "product", source = "productId", qualifiedByName = "getProduct")
    CartItemResponse toCartItemResponse(CartItem cartItem);

}
