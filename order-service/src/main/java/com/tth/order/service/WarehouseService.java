package com.tth.order.service;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.request.warehouse.CreateWarehouseRequest;
import com.tth.order.dto.request.warehouse.UpdateWarehouseRequest;
import com.tth.order.dto.response.warehouse.WarehouseResponse;

import java.util.Map;

public interface WarehouseService {

    WarehouseResponse findById(String warehouseId);

    WarehouseResponse create(CreateWarehouseRequest request);

    WarehouseResponse update(String warehouseId, UpdateWarehouseRequest request);

    void delete(String warehouseId);

    PageResponse<WarehouseResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
