package com.tth.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tth.commonlibrary.enums.OrderStatus;
import com.tth.commonlibrary.enums.OrderType;
import com.tth.commonlibrary.utils.GeneratorUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

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

    @NotNull(message = "{order.orderNumber.notNull}")
    @Column(name = "order_number", nullable = false, unique = true, length = 36, updatable = false)
    private String orderNumber;

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
    private Set<OrderItem> orderItems;

    public void prePersist() {
        super.prePersist();

        if (this.orderNumber == null) {
            this.orderNumber = GeneratorUtils.generateOrderNumber();
        }
    }

}
