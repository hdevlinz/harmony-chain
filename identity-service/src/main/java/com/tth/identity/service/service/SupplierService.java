package com.tth.identity.service.service;

import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.supplier.SupplierResponse;
import com.tth.identity.service.entity.Supplier;

import java.util.Map;

public interface SupplierService {

    Supplier findById(String id);

    PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size);
}
