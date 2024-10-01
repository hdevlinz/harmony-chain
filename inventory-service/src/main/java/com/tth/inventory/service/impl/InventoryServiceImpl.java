package com.tth.inventory.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.inventory.entity.Inventory;
import com.tth.inventory.mapper.InventoryMapper;
import com.tth.inventory.repository.InventoryRepository;
import com.tth.inventory.service.InventoryService;
import com.tth.inventory.service.specification.InventorySpecification;
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
    private final InventoryMapper inventoryMapper;

    @Override
    public PageResponse<InventoryResponse> findAll(Map<String, String> params, int page, int size) {
        Specification<Inventory> spec = InventorySpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<InventoryResponse> result = this.inventoryRepository.findAll(spec, pageable)
                .map(this.inventoryMapper::toInventoryResponse);

        return PageResponse.of(result);
    }

    @Override
    public InventoryResponse findById(String inventoryId) {
        Inventory inventory = this.inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        return this.inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse create(InventoryRequestCreate request) {
        Inventory inventory = this.inventoryMapper.toInventory(request);
        inventory = this.inventoryRepository.save(inventory);

        return this.inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse update(String inventoryId, InventoryRequestUpdate request) {
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

}
