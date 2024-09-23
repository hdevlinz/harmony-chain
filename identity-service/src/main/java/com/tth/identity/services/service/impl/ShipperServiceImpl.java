package com.tth.identity.services.service.impl;

import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.shipper.ShipperResponse;
import com.tth.identity.services.entity.Shipper;
import com.tth.identity.services.enums.ErrorCode;
import com.tth.identity.services.exception.AppException;
import com.tth.identity.services.mapper.ShipperMapper;
import com.tth.identity.services.repository.ShipperRepository;
import com.tth.identity.services.repository.specification.ShipperSpecification;
import com.tth.identity.services.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
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
