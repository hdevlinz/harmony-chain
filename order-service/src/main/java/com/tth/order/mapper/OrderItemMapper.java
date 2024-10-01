package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.OrderItemResponse;
import com.tth.order.entity.OrderItem;
import com.tth.order.mapper.helper.MappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderItemMapper {

    @Named("toOrderItemResponse")
    @Mapping(target = "product", source = "productId", qualifiedByName = "getProductById")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

}
