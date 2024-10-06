package com.tth.shipping.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.shipping.entity.DeliverySchedule;

import java.util.Map;

public interface DeliveryScheduleService {

    PageResponse<DeliverySchedule> findAll(Map<String, String> params, int page, int size);

}
