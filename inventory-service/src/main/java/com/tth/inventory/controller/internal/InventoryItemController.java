package com.tth.inventory.controller.internal;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.inventory.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/items", produces = "application/json; charset=UTF-8")
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping(path = "/batch")
    public ResponseEntity<?> listInventoryItemsInBatch(@RequestBody List<String> inventoryItemIds) {
        List<InventoryItemResponse> inventoryItems = this.inventoryItemService.findAllInBatch(inventoryItemIds);

        return ResponseEntity.ok(APIResponse.<List<InventoryItemResponse>>builder().result(inventoryItems).build());
    }

    @GetMapping(path = "/total-quantity")
    public ResponseEntity<?> getTotalQuantityByWarehouseId(@RequestParam String warehouseId) {
        Map<String, Float> result = this.inventoryItemService.getTotalQuantityByWarehouseId(warehouseId);

        return ResponseEntity.ok(APIResponse.<Map<String, Float>>builder().result(result).build());
    }

}
