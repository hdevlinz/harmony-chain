package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.order.entity.Tax;

import java.util.Map;

public interface TaxService {

    PageResponse<Tax> findAllWithFilter(Map<String, String> params, int page, int size);

}
