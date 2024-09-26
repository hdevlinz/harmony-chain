package com.tth.shipping.service;

import com.tth.shipping.dto.PageResponse;
import com.tth.shipping.entity.Shipment;

import java.util.Map;

public interface ShipmentService {

    PageResponse<Shipment> findAllWithFilter(Map<String, String> params, int page, int size);

}
