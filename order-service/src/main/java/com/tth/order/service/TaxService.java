package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.order.tax.TaxResponse;

import java.util.Map;

public interface TaxService {

    PageResponse<TaxResponse> findAll(Map<String, String> params, int page, int size);

}
