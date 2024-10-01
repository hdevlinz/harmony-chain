package com.tth.profile.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.profile.entity.Supplier;
import com.tth.profile.mapper.SupplierMapper;
import com.tth.profile.repository.SupplierRepository;
import com.tth.profile.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponse findById(String supplierId) {
        Supplier supplier = this.supplierRepository.findById(supplierId)
                .orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));

        return this.supplierMapper.toSupplierResponse(supplier);
    }

    @Override
    public PageResponse<SupplierResponse> findAll(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SupplierResponse> result = this.supplierRepository.filter(params, pageable)
                .map(this.supplierMapper::toSupplierResponse);

        return PageResponse.of(result);
    }

}
