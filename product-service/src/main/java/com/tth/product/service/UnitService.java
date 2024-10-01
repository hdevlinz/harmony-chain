package com.tth.product.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.unit.UnitResponse;

import java.util.Map;

public interface UnitService {

    PageResponse<UnitResponse> findAll(Map<String, String> params, int page, int size);

}
