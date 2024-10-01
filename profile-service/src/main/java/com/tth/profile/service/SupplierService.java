package com.tth.profile.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;

import java.util.Map;

public interface SupplierService {

    SupplierResponse findById(String supplierId);

    PageResponse<SupplierResponse> findAll(Map<String, String> params, int page, int size);

}
