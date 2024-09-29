package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.warehouse.CreateWarehouseRequest;
import com.tth.commonlibrary.dto.request.warehouse.UpdateWarehouseRequest;
import com.tth.commonlibrary.dto.response.warehouse.WarehouseResponse;

import java.util.Map;

public interface WarehouseService {

    WarehouseResponse findById(String warehouseId);

    WarehouseResponse create(CreateWarehouseRequest request);

    WarehouseResponse update(String warehouseId, UpdateWarehouseRequest request);

    void delete(String warehouseId);

    PageResponse<WarehouseResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
