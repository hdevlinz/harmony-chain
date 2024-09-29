package com.tth.inventory.service;

import com.tth.order.dto.PageResponse;
import com.tth.order.entity.Tax;

import java.util.Map;

public interface TaxService {

    PageResponse<Tax> findAllWithFilter(Map<String, String> params, int page, int size);

}
