package com.tth.profile.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.profile.entity.Customer;
import com.tth.profile.mapper.CustomerMapper;
import com.tth.profile.repository.CustomerRepository;
import com.tth.profile.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse findById(String customerId) {
        Customer customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        return this.customerMapper.toCustomerResponse(customer);
    }

    @Override
    public PageResponse<CustomerResponse> findAll(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CustomerResponse> result = this.customerRepository.filter(params, pageable)
                .map(this.customerMapper::toCustomerResponse);

        return PageResponse.of(result);
    }

}
