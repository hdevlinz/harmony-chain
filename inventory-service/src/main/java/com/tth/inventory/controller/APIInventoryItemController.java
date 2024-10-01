package com.tth.inventory.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.inventory.service.InventoryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/items", produces = "application/json; charset=UTF-8")
public class APIInventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping(path = "/batch")
    public ResponseEntity<?> listInventoryItemsInBatch(@RequestBody List<String> inventoryItemIds) {
        List<InventoryItemResponse> inventoryItems = this.inventoryItemService.findAllInBatch(inventoryItemIds);

        return ResponseEntity.ok(APIResponse.<List<InventoryItemResponse>>builder().result(inventoryItems).build());
    }

    @GetMapping
    public ResponseEntity<?> listInventoryItems(@RequestParam Map<String, String> params) {
        List<InventoryItemResponse> inventoryItems = this.inventoryItemService.findAll(params);

        return ResponseEntity.ok(APIResponse.<List<InventoryItemResponse>>builder().result(inventoryItems).build());
    }

    @PostMapping
    public ResponseEntity<?> createInventoryItem(@RequestBody @Valid InventoryItemRequestCreate request) {
        InventoryItemResponse createdInventoryItem = this.inventoryItemService.create(request);

        return ResponseEntity.ok(APIResponse.<InventoryItemResponse>builder().result(createdInventoryItem).build());
    }

    @GetMapping(path = "/{inventoryItemId}")
    public ResponseEntity<?> getInventoryItem(@PathVariable String inventoryItemId) {
        InventoryItemResponse inventoryItem = this.inventoryItemService.findById(inventoryItemId);

        return ResponseEntity.ok(APIResponse.<InventoryItemResponse>builder().result(inventoryItem).build());
    }

    @PutMapping(path = "/{inventoryItemId}")
    public ResponseEntity<?> updateInventoryItem(@PathVariable String inventoryItemId, @RequestBody @Valid InventoryItemRequestUpdate request) {
        InventoryItemResponse updatedInventoryItem = this.inventoryItemService.update(inventoryItemId, request);

        return ResponseEntity.ok(APIResponse.<InventoryItemResponse>builder().result(updatedInventoryItem).build());
    }

    @GetMapping(path = "/total-quantity")
    public ResponseEntity<?> getTotalQuantityByWarehouseId(@RequestParam String warehouseId) {
        Map<String, Float> result = this.inventoryItemService.getTotalQuantityByWarehouseId(warehouseId);

        return ResponseEntity.ok(APIResponse.<Map<String, Float>>builder().result(result).build());
    }

}
