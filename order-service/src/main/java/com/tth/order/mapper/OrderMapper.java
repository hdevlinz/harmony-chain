package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.OrderItemResponse;
import com.tth.commonlibrary.dto.response.order.OrderResponse;
import com.tth.order.entity.Order;
import com.tth.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("toOrderResponse")
    @Mapping(target = "orderDate", source = "createdAt")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderItemResponse")
    OrderResponse toOrderResponse(Order order);

    @Named("toOrderItemResponse")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

}
