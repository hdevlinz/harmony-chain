package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.OrderResponse;
import com.tth.order.entity.Order;
import com.tth.order.mapper.helper.MappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingHelper.class, OrderItemMapper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

    @Named("toOrderResponse")
    @Mapping(target = "orderDate", source = "createdAt")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderItemResponse")
    OrderResponse toOrderResponse(Order order);

}
