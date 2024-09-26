package com.tth.order.service;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.request.inventory.CreateInventoryRequest;
import com.tth.order.dto.request.inventory.UpdateInventoryRequest;
import com.tth.order.dto.response.inventory.InventoryResponse;

import java.util.Map;

public interface InventoryService {

    InventoryResponse findById(String id);

    InventoryResponse create(CreateInventoryRequest request);

    InventoryResponse update(String inventoryId, UpdateInventoryRequest request);

    void delete(String inventoryId);

    PageResponse<InventoryResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
