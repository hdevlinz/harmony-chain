package com.tth.identity.services.service;

import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.customer.CustomerResponse;
import com.tth.identity.services.entity.Customer;

import java.util.Map;

public interface CustomerService {

    Customer findById(String id);

    PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
