package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.supplier.SupplierResponse;
import com.tth.identity.entity.Supplier;

import java.util.Map;

public interface SupplierService {

    Supplier findById(String id);

    PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
