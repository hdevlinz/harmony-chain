package com.tth.product.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.unit.UnitResponse;
import com.tth.product.mapper.UnitMapper;
import com.tth.product.repository.UnitRepository;
import com.tth.product.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Override
    public PageResponse<UnitResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UnitResponse> result = this.unitRepository.filter(params, pageable).map(this.unitMapper::toUnitResponse);

        return PageResponse.of(result);
    }

}
