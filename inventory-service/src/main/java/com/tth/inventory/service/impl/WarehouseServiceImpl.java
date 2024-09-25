package com.tth.inventory.service.impl;

import com.tth.inventory.dto.PageResponse;
import com.tth.inventory.entity.Warehouse;
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

    @Override
    public PageResponse<Warehouse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Warehouse> spec = WarehouseSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Warehouse> result = this.warehouseRepository.findAll(spec, pageable);

        return PageResponse.of(result);
    }

}
