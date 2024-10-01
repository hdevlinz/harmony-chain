package com.tth.shipping.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.shipping.entity.Shipment;
import com.tth.shipping.repository.ShipmentRepository;
import com.tth.shipping.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Override
    public PageResponse<Shipment> findAll(Map<String, String> params, int page, int size) {
        return null;
    }

}
