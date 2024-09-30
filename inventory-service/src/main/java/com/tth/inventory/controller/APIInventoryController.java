package com.tth.inventory.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;
import com.tth.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/inventories", produces = "application/json; charset=UTF-8")
public class APIInventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> listInventories(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "5") int size) {
        return ResponseEntity.ok(this.inventoryService.findAllWithFilter(params, page, size));
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@RequestBody @Valid InventoryRequestCreate request) {
        InventoryResponse createdInventory = this.inventoryService.create(request);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(createdInventory).build());
    }

    @GetMapping(path = "/{inventoryId}")
    public ResponseEntity<?> getInventory(@PathVariable String inventoryId) {
        InventoryResponse inventory = this.inventoryService.findById(inventoryId);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(inventory).build());
    }

    @PatchMapping(path = "/{inventoryId}")
    public ResponseEntity<?> updateInventory(@PathVariable String inventoryId, @RequestBody @Valid InventoryRequestUpdate request) {
        InventoryResponse updatedInventory = this.inventoryService.update(inventoryId, request);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(updatedInventory).build());
    }

    @DeleteMapping(path = "/{inventoryId}")
    public ResponseEntity<?> deleteInventory(@PathVariable String inventoryId) {
        this.inventoryService.delete(inventoryId);

        return ResponseEntity.noContent().build();
    }

}
