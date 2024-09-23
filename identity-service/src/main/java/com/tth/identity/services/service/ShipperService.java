package com.tth.identity.services.service;

import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.shipper.ShipperResponse;
import com.tth.identity.services.entity.Shipper;

import java.util.Map;

public interface ShipperService {

    Shipper findById(String id);

    PageResponse<ShipperResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
