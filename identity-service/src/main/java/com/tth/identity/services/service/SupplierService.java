package com.tth.identity.services.service;

import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.supplier.SupplierResponse;
import com.tth.identity.services.entity.Supplier;

import java.util.Map;

public interface SupplierService {

    Supplier findById(String id);

    PageResponse<SupplierResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
