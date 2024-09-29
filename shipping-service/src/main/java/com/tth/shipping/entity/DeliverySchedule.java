package com.tth.shipping.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.commonlibrary.enums.DeliveryMethodType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "delivery_schedule")
public class DeliverySchedule extends BaseEntity implements Serializable {

    @Field(name = "shipper_id")
    private String shipperId;

    @Field(name = "warehouse_id")
    private String warehouseId;

    @NotNull(message = "{deliverySchedule.scheduledDate.notNull}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Field(name = "schedule_date")
    private LocalDate scheduledDate;

    @Builder.Default
    @NotNull(message = "{deliverySchedule.method.notNull}")
    @Field
    private DeliveryMethodType method = DeliveryMethodType.EXPRESS;

    //    @OneToMany(mappedBy = "deliverySchedule", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Shipment> shipments;

}
