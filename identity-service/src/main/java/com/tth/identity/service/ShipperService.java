package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.shipper.ShipperResponse;

import java.util.Map;

public interface ShipperService {

    ShipperResponse findById(String shipperId);

    PageResponse<ShipperResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
