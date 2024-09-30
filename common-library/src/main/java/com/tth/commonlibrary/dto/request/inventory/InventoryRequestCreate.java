package com.tth.commonlibrary.dto.request.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestCreate {

    @NotNull(message = "{inventory.name.notNull}")
    @NotBlank(message = "{inventory.name.notNull}")
    private String name;

    @NotNull(message = "{inventory.warehouse.notNull}")
    @NotBlank(message = "{inventory.warehouse.notNull}")
    private String warehouseId;

}
