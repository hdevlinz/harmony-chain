package com.tth.inventory.service.impl;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.request.inventory.CreateInventoryRequest;
import com.tth.order.dto.request.inventory.UpdateInventoryRequest;
import com.tth.order.dto.response.inventory.InventoryResponse;
import com.tth.order.entity.Inventory;
import com.tth.order.enums.ErrorCode;
import com.tth.order.exception.AppException;
import com.tth.order.mapper.InventoryMapper;
import com.tth.order.repository.InventoryRepository;
import com.tth.order.repository.WarehouseRepository;
import com.tth.order.service.InventoryService;
import com.tth.order.service.specification.InventorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse findById(String inventoryId) {
        Inventory inventory = this.inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return this.inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse create(CreateInventoryRequest request) {
        Inventory inventory = this.inventoryMapper.toInventory(request);
        inventory = this.inventoryRepository.save(inventory);

        return this.inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse update(String inventoryId, UpdateInventoryRequest request) {
        Inventory inventory = this.inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        inventory = this.inventoryMapper.updateInventory(inventory, request);
        inventory = this.inventoryRepository.save(inventory);

        return this.inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public void delete(String inventoryId) {
        this.inventoryRepository.deleteById(inventoryId);
    }

    @Override
    public PageResponse<InventoryResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Inventory> spec = InventorySpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InventoryResponse> result = this.inventoryRepository.findAll(spec, pageable)
                .map(this.inventoryMapper::toInventoryResponse);

        return PageResponse.of(result);
    }

}
