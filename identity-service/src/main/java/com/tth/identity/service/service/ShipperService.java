package com.tth.identity.service.service;

import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.shipper.ShipperResponse;
import com.tth.identity.service.entity.Shipper;

import java.util.Map;

public interface ShipperService {

    Shipper findById(String id);

    PageResponse<ShipperResponse> findAllWithFilter(Map<String, String> params, int page, int size);
}
