package com.tth.shipping.entity;

import com.tth.shipping.enums.ShipmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("shipment")
public class Shipment extends BaseEntity implements Serializable {

    @Field(name = "shipper_id")
    private String shipperId;

    @Field(name = "warehouse_id")
    private String warehouseId;

    @Builder.Default
    @NotNull(message = "{shipment.cost.notNull}")
    @Field
    private BigDecimal cost = new BigDecimal(0);

    private String note;

    @Field(name = "current_location")
    private String currentLocation;

    @Builder.Default
    @NotNull(message = "{shipment.trackingNumber.notNull}")
    @Field(name = "tracking_number")
    private String trackingNumber = String.valueOf(UUID.randomUUID());

    @Builder.Default
    @NotNull(message = "{shipment.status.notNull}")
    @Field
    private ShipmentStatus status = ShipmentStatus.IN_TRANSIT;

    @Field(name = "delivery_schedule_id")
    private DeliverySchedule deliverySchedule;

}
