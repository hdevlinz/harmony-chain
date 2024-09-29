package com.tth.inventory.dto.response.inventory;

import com.tth.order.dto.response.warehouse.WarehouseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    private String name;

    private WarehouseResponse warehouse;

    private Set<InventoryDetailsResponse> inventoryDetails;

}
