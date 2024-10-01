package com.tth.shipping.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.shipping.entity.Shipment;

import java.util.Map;

public interface ShipmentService {

    PageResponse<Shipment> findAll(Map<String, String> params, int page, int size);

}
