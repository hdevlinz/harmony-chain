package com.tth.commonlibrary.dto.response.inventory;

import com.tth.commonlibrary.dto.response.warehouse.WarehouseResponse;
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

    private String id;

    private String name;

    private WarehouseResponse warehouse;

    private Set<InventoryItemResponse> inventoryItems;

}
