package com.tth.profile.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.profile.entity.Carrier;
import com.tth.profile.mapper.CarrierMapper;
import com.tth.profile.repository.CarrierRepository;
import com.tth.profile.service.CarrierService;
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
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;
    private final CarrierMapper carrierMapper;

    @Override
    public CarrierResponse findById(String carrierId) {
        Carrier shipper = this.carrierRepository.findById(carrierId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPPED_NOT_FOUND));

        return this.carrierMapper.toCarrierResponse(shipper);
    }

    @Override
    public PageResponse<CarrierResponse> findAll(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CarrierResponse> result = this.carrierRepository.filter(params, pageable)
                .map(this.carrierMapper::toCarrierResponse);

        return PageResponse.of(result);
    }

}
