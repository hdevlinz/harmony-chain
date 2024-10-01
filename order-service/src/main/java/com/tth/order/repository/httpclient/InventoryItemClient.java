package com.tth.order.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "inventory-item-client", url = "${app.services.inventory.url}")
public interface InventoryItemClient {

    @PostMapping(path = "/items/batch")
    APIResponse<List<InventoryItemResponse>> listInventoryItemsInBatch(@RequestBody List<String> inventoryItemIds);

    @GetMapping(path = "/items")
    APIResponse<List<InventoryItemResponse>> listInventoryItems(@RequestParam Map<String, String> params);

    @PostMapping(path = "/items")
    APIResponse<InventoryItemResponse> createInventoryItem(@RequestBody @Valid InventoryItemRequestCreate request);

    @GetMapping(path = "/items/{inventoryItemId}")
    APIResponse<InventoryItemResponse> getInventoryItem(@PathVariable String inventoryItemId);

    @PutMapping(path = "/items/{inventoryItemId}")
    APIResponse<InventoryItemResponse> updateInventoryItem(@PathVariable String inventoryItemId, @RequestBody @Valid InventoryItemRequestUpdate request);

    @GetMapping(path = "/items/total-quantity")
    APIResponse<Map<String, Float>> getTotalQuantityByWarehouseId(@RequestParam String warehouseId);

}
