package com.tth.commonlibrary.dto.request.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequestCreate {

    @NotNull(message = "{warehouse.name.notNull}")
    @NotBlank(message = "{warehouse.name.notNull}")
    private String name;

    @NotNull(message = "{warehouse.location.notNull}")
    @NotBlank(message = "{warehouse.location.notNull}")
    private String location;

    @NotNull(message = "{warehouse.capacity.notNull}")
    private Float capacity;

    @NotNull(message = "{warehouse.cost.notNull}")
    private BigDecimal cost;

}
