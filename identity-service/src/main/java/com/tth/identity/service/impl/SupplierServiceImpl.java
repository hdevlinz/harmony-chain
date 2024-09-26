package com.tth.identity.service.impl;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.supplier.SupplierResponse;
import com.tth.identity.entity.Supplier;
import com.tth.identity.enums.ErrorCode;
import com.tth.identity.exception.AppException;
import com.tth.identity.mapper.SupplierMapper;
import com.tth.identity.repository.SupplierRepository;
import com.tth.identity.service.SupplierService;
import com.tth.identity.service.specification.SupplierSpecification;
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
    public PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Supplier> spec = SupplierSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SupplierResponse> result = this.supplierRepository.findAll(spec, pageable).map(this.supplierMapper::toSupplierResponse);

        return PageResponse.of(result);
    }

}
