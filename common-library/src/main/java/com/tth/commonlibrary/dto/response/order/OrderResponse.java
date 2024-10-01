package com.tth.commonlibrary.dto.response.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.commonlibrary.enums.OrderStatus;
import com.tth.commonlibrary.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String id;

    private OrderType type;

    private OrderStatus status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime orderDate;

    private String orderNumber;

    private String invoiceNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDelivery;

    private Set<OrderItemResponse> orderItems;

}
