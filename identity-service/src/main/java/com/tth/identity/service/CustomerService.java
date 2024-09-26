package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.customer.CustomerResponse;

import java.util.Map;

public interface CustomerService {

    CustomerResponse findById(String id);

    PageResponse<CustomerResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
