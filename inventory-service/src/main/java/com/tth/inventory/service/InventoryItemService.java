package com.tth.inventory.service;

import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;

import java.util.List;
import java.util.Map;

public interface InventoryItemService {

    List<InventoryItemResponse> findAllInBatch(List<String> inventoryItemIds);

    List<InventoryItemResponse> findAll(Map<String, String> params);

    InventoryItemResponse findById(String inventoryItemId);

    InventoryItemResponse create(InventoryItemRequestCreate request);

    InventoryItemResponse update(String inventoryItemId, InventoryItemRequestUpdate request);

    void delete(String inventoryItemId);

    Map<String, Float> getTotalQuantityByWarehouseId(String warehouseId);

}
