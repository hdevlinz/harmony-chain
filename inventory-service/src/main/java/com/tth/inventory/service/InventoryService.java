package com.tth.inventory.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;

import java.util.Map;

public interface InventoryService {

    PageResponse<InventoryResponse> findAll(Map<String, String> params, int page, int size);

    InventoryResponse findById(String id);

    InventoryResponse create(InventoryRequestCreate request);

    InventoryResponse update(String inventoryId, InventoryRequestUpdate request);

    void delete(String inventoryId);

}
