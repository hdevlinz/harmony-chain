package com.tth.inventory.mapper;

import com.tth.order.dto.response.OrderDetailsReponse;
import com.tth.order.dto.response.OrderResponse;
import com.tth.order.entity.Order;
import com.tth.order.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Named("toOrderResponse")
    @Mapping(target = "orderDate", source = "createdAt")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")
    @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "toOrderDetailsReponse")
    OrderResponse toOrderResponse(Order order);

    @Named("toOrderDetailsReponse")
    OrderDetailsReponse toOrderDetailsReponse(OrderDetails orderDetails);

}
