package com.tth.inventory.service;

import com.tth.inventory.dto.PageResponse;
import com.tth.inventory.entity.Inventory;

import java.util.Map;

public interface InventoryService {

    PageResponse<Inventory> findAllWithFilter(Map<String, String> params, int page, int size);

}
