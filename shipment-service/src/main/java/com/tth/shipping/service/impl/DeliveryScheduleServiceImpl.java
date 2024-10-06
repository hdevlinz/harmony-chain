package com.tth.shipping.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.shipping.entity.DeliverySchedule;
import com.tth.shipping.repository.DeliveryScheduleRepository;
import com.tth.shipping.service.DeliveryScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryScheduleServiceImpl implements DeliveryScheduleService {

    private final DeliveryScheduleRepository deliveryScheduleRepository;

    @Override
    public PageResponse<DeliverySchedule> findAll(Map<String, String> params, int page, int size) {
        return null;
    }

}
