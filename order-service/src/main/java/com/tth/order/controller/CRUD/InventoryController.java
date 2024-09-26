package com.tth.order.controller.CRUD;

import com.tth.order.dto.APIResponse;
import com.tth.order.dto.request.inventory.CreateInventoryRequest;
import com.tth.order.dto.request.inventory.UpdateInventoryRequest;
import com.tth.order.dto.response.inventory.InventoryResponse;
import com.tth.order.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/inventory", produces = "application/json; charset=UTF-8")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> listInventories(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "5") int size) {
        return ResponseEntity.ok(this.inventoryService.findAllWithFilter(params, page, size));
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@RequestBody @Valid CreateInventoryRequest request) {
        InventoryResponse createdInventory = this.inventoryService.create(request);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(createdInventory).build());
    }

    @GetMapping(path = "/{inventoryId}")
    public ResponseEntity<?> getInventory(@PathVariable String inventoryId) {
        InventoryResponse inventory = this.inventoryService.findById(inventoryId);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(inventory).build());
    }

    @PatchMapping(path = "/{inventoryId}")
    public ResponseEntity<?> updateInventory(@PathVariable String inventoryId, @RequestBody @Valid UpdateInventoryRequest request) {
        InventoryResponse updatedInventory = this.inventoryService.update(inventoryId, request);

        return ResponseEntity.ok(APIResponse.<InventoryResponse>builder().result(updatedInventory).build());
    }

    @DeleteMapping(path = "/{inventoryId}")
    public ResponseEntity<?> deleteInventory(@PathVariable String inventoryId) {
        this.inventoryService.delete(inventoryId);

        return ResponseEntity.noContent().build();
    }

}
