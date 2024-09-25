package com.tth.inventory.service;

import com.tth.inventory.dto.PageResponse;
import com.tth.inventory.entity.Warehouse;

import java.util.Map;

public interface WarehouseService {

    PageResponse<Warehouse> findAllWithFilter(Map<String, String> params, int page, int size);

}
