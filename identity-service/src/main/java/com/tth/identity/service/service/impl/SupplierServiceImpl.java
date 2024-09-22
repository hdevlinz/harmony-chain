package com.tth.identity.service.service.impl;

import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.supplier.SupplierResponse;
import com.tth.identity.service.entity.Supplier;
import com.tth.identity.service.enums.ErrorCode;
import com.tth.identity.service.exception.AppException;
import com.tth.identity.service.mapper.SupplierMapper;
import com.tth.identity.service.repository.SupplierRepository;
import com.tth.identity.service.repository.UserRepository;
import com.tth.identity.service.repository.specification.SupplierSpecification;
import com.tth.identity.service.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper supplierMapper;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    @Override
    public Supplier findById(String id) {
        return this.supplierRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.SUPPLIER_NOT_FOUND));
    }

    @Override
    public PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Supplier> spec = SupplierSpecification.filterSuppliers(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<SupplierResponse> result = this.supplierRepository.findAll(spec, pageable).map(this.supplierMapper::toSupplierResponse);

        return PageResponse.of(result);
    }
}
