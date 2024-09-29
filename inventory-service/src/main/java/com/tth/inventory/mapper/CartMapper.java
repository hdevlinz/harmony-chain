package com.tth.inventory.mapper;

import com.tth.order.dto.response.cart.CartDetailsResponse;
import com.tth.order.entity.CartDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Named("toCartDetailsResponse")
    CartDetailsResponse toCartDetailsResponse(CartDetails cartDetails);

}
