package com.tth.profile.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;

import java.util.Map;

public interface CarrierService {

    CarrierResponse findById(String carrierId);

    PageResponse<CarrierResponse> findAll(Map<String, String> params, int page, int size);

}
