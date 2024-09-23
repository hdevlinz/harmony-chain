package com.tth.identity.services.service.impl;

import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.customer.CustomerResponse;
import com.tth.identity.services.entity.Customer;
import com.tth.identity.services.enums.ErrorCode;
import com.tth.identity.services.exception.AppException;
import com.tth.identity.services.mapper.CustomerMapper;
import com.tth.identity.services.repository.CustomerRepository;
import com.tth.identity.services.repository.specification.CustomerSpecification;
import com.tth.identity.services.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Customer findById(String id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Customer> spec = CustomerSpecification.filterCustomers(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CustomerResponse> result = this.customerRepository.findAll(spec, pageable).map(this.customerMapper::toCustomerResponse);

        return PageResponse.of(result);
    }

}
