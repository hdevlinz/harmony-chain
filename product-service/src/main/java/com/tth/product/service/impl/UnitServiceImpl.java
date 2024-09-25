package com.tth.product.service.impl;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.unit.UnitResponse;
import com.tth.product.entity.Unit;
import com.tth.product.mapper.UnitMapper;
import com.tth.product.repository.UnitRepository;
import com.tth.product.service.UnitService;
import com.tth.product.service.specification.UnitSpecification;
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
public class UnitServiceImplement implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public PageResponse<UnitResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Unit> spec = UnitSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UnitResponse> result = this.unitRepository.findAll(spec, pageable).map(this.unitMapper::toUnitResponse);

        return PageResponse.of(result);
    }

}
