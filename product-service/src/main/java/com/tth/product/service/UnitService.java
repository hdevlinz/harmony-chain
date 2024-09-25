package com.tth.product.service;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.unit.UnitResponse;

import java.util.Map;

public interface UnitService {

    PageResponse<UnitResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
