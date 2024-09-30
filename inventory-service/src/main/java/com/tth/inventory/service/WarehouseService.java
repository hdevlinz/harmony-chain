package com.tth.inventory.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestCreate;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.warehouse.WarehouseResponse;

import java.util.Map;

public interface WarehouseService {

    WarehouseResponse findById(String warehouseId);

    WarehouseResponse create(WarehouseRequestCreate request);

    WarehouseResponse update(String warehouseId, WarehouseRequestUpdate request);

    void delete(String warehouseId);

    PageResponse<WarehouseResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
