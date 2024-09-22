package com.tth.identity.service.service;

import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.customer.CustomerResponse;
import com.tth.identity.service.entity.Customer;

import java.util.Map;

public interface CustomerService {

    Customer findById(String id);

    PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size);
}
