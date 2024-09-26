package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.supplier.SupplierResponse;

import java.util.Map;

public interface SupplierService {

    SupplierResponse findById(String supplierId);

    PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
