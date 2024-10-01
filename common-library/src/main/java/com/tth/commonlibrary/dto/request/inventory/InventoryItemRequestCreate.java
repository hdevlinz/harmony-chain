package com.tth.commonlibrary.dto.request.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequestCreate {

    private String inventoryId;

    private String productId;

    private Float quantity;

}
