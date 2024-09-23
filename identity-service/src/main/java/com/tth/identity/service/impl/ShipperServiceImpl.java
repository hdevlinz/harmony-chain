package com.tth.identity.service.impl;

import com.tth.identity.dto.response.PageResponse;
import com.tth.identity.dto.response.shipper.ShipperResponse;
import com.tth.identity.entity.Shipper;
import com.tth.identity.enums.ErrorCode;
import com.tth.identity.exception.AppException;
import com.tth.identity.mapper.ShipperMapper;
import com.tth.identity.repository.ShipperRepository;
import com.tth.identity.repository.specification.ShipperSpecification;
import com.tth.identity.service.ShipperService;
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
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;
    private final ShipperMapper shipperMapper;

    @Override
    public Shipper findById(String id) {
        return this.shipperRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SHIPPED_NOT_FOUND));
    }

    @Override
    public PageResponse<ShipperResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Shipper> spec = ShipperSpecification.filterShippers(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ShipperResponse> result = this.shipperRepository.findAll(spec, pageable).map(this.shipperMapper::toShipperResponse);

        return PageResponse.of(result);
    }

}
