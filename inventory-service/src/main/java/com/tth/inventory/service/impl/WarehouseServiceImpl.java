package com.tth.inventory.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestCreate;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.warehouse.WarehouseResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.inventory.entity.Warehouse;
import com.tth.inventory.mapper.WarehouseMapper;
import com.tth.inventory.repository.WarehouseRepository;
import com.tth.inventory.service.WarehouseService;
import com.tth.inventory.service.specification.WarehouseSpecification;
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
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public WarehouseResponse findById(String warehouseId) {
        Warehouse warehouse = this.warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_FOUND));

        return this.warehouseMapper.toWarehouseResponse(warehouse);
    }

    @Override
    public WarehouseResponse create(WarehouseRequestCreate request) {
        if (this.warehouseRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.WAREHOUSE_EXISTS);
        }

        Warehouse warehouse = this.warehouseMapper.toWarehouse(request);
        warehouse = this.warehouseRepository.save(warehouse);

        return this.warehouseMapper.toWarehouseResponse(warehouse);
    }

    @Override
    public WarehouseResponse update(String warehouseId, WarehouseRequestUpdate request) {
        if (this.warehouseRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.WAREHOUSE_EXISTS);
        }

        Warehouse warehouse = this.warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_FOUND));

        warehouse = this.warehouseMapper.updateWarehouse(warehouse, request);
        warehouse = this.warehouseRepository.save(warehouse);

        return this.warehouseMapper.toWarehouseResponse(warehouse);
    }

    @Override
    public void delete(String warehouseId) {
        this.warehouseRepository.deleteById(warehouseId);
    }

    @Override
    public PageResponse<WarehouseResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Warehouse> spec = WarehouseSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<WarehouseResponse> result = this.warehouseRepository.findAll(spec, pageable)
                .map(this.warehouseMapper::toWarehouseResponse);

        return PageResponse.of(result);
    }

}
