package com.tth.order.dto.request;

import com.tth.order.enums.OrderStatus;
import com.tth.order.enums.OrderType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "{order.type.notNull}")
    private OrderType type;

    private OrderStatus status;

    @Valid
    private Set<OrderDetailsRequest> orderDetails;

    private LocalDateTime createdAt;

    private Boolean paid;

    private String inventoryId; // Cho đơn hàng nhập

}
