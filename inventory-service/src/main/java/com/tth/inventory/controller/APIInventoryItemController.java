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

    @DeleteMapping(path = "/{inventoryItemId}")
    public ResponseEntity<?> deleteInventory(@PathVariable String inventoryItemId) {
        this.inventoryItemService.delete(inventoryItemId);

        return ResponseEntity.noContent().build();
    }

}
