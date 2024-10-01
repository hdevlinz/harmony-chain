package com.tth.inventory.service.impl;

import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.inventory.entity.InventoryItem;
import com.tth.inventory.mapper.InventoryItemMapper;
import com.tth.inventory.repository.InventoryItemRepository;
import com.tth.inventory.service.InventoryItemService;
import com.tth.inventory.service.specification.InventoryItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryItemServiceImpl implements InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemMapper inventoryItemMapper;

    @Override
    public List<InventoryItemResponse> findAllInBatch(List<String> inventoryItemIds) {
        return this.inventoryItemRepository.findAllById(inventoryItemIds)
                .stream().map(this.inventoryItemMapper::toInventoryItemResponse).toList();
    }

    @Override
    public List<InventoryItemResponse> findAll(Map<String, String> params) {
        Specification<InventoryItem> spec = InventoryItemSpecification.filter(params);

        return this.inventoryItemRepository.findAll(spec)
                .stream().map(this.inventoryItemMapper::toInventoryItemResponse).toList();
    }

    @Override
    public InventoryItemResponse findById(String inventoryItemId) {
        InventoryItem inventoryItem = this.inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

        return this.inventoryItemMapper.toInventoryItemResponse(inventoryItem);
    }

    @Override
    public InventoryItemResponse create(InventoryItemRequestCreate request) {
        InventoryItem inventoryItem = this.inventoryItemMapper.toInventoryItem(request);
        inventoryItem = this.inventoryItemRepository.save(inventoryItem);

        return this.inventoryItemMapper.toInventoryItemResponse(inventoryItem);
    }

    @Override
    public InventoryItemResponse update(String inventoryItemId, InventoryItemRequestUpdate request) {
        InventoryItem inventoryItem = this.inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

        inventoryItem = this.inventoryItemMapper.updateInventoryItem(inventoryItem, request);
        inventoryItem = this.inventoryItemRepository.save(inventoryItem);

        return this.inventoryItemMapper.toInventoryItemResponse(inventoryItem);
    }

    @Override
    public void delete(String inventoryItemId) {
        InventoryItem inventoryItem = this.inventoryItemRepository.findById(inventoryItemId)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

        this.inventoryItemRepository.delete(inventoryItem);
    }

    @Override
    public Map<String, Float> getTotalQuantityByWarehouseId(String warehouseId) {
        Float totalQuantity = this.inventoryItemRepository.getTotalQuantityByWarehouseId(warehouseId);

        return Map.of("totalQuantity", totalQuantity);
    }

}
