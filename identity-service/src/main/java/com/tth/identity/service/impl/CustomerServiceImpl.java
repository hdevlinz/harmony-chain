package com.tth.identity.service.impl;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.customer.CustomerResponse;
import com.tth.identity.entity.Customer;
import com.tth.identity.enums.ErrorCode;
import com.tth.identity.exception.AppException;
import com.tth.identity.mapper.CustomerMapper;
import com.tth.identity.repository.CustomerRepository;
import com.tth.identity.service.CustomerService;
import com.tth.identity.service.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerResponse findById(String customerId) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        return this.customerMapper.toCustomerResponse(customer);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Customer> spec = CustomerSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CustomerResponse> result = this.customerRepository.findAll(spec, pageable).map(this.customerMapper::toCustomerResponse);

        return PageResponse.of(result);
    }

}
