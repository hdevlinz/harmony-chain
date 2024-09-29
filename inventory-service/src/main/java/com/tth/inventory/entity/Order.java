package com.tth.inventory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tth.order.enums.OrderStatus;
import com.tth.order.enums.OrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"order\"", indexes = {
        @Index(name = "order_number_index", columnList = "order_number", unique = true),
})
public class Order extends BaseEntity implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "shipment_id")
    private String shipmentId;

    @Builder.Default
    @NotNull(message = "{order.orderNumber.notNull}")
    @Column(name = "order_number", nullable = false, unique = true, length = 36, updatable = false)
    private String orderNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{order.type.notNull}")
    @Column(name = "order_type", nullable = false)
    private OrderType type = OrderType.OUTBOUND;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{order.status.notNull}")
    @Column(name = "order_status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "expected_delivery")
    private LocalDate expectedDelivery;

    @JsonIgnore
    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Invoice invoice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetails> orderDetails;

}
