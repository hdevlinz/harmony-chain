package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.response.shipper.ShipperResponse;
import com.tth.identity.entity.Shipper;

import java.util.Map;

public interface ShipperService {

    Shipper findById(String id);

    PageResponse<ShipperResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
