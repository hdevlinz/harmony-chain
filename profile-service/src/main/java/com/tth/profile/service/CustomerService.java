package com.tth.profile.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;

import java.util.Map;

public interface CustomerService {

    CustomerResponse findById(String id);

    PageResponse<CustomerResponse> findAll(Map<String, String> params, int page, int size);

}
