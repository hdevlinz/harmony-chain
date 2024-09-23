package com.tth.identity.service;

import com.tth.identity.dto.response.PageResponse;
import com.tth.identity.dto.response.customer.CustomerResponse;
import com.tth.identity.entity.Customer;

import java.util.Map;

public interface CustomerService {

    Customer findById(String id);

    PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
