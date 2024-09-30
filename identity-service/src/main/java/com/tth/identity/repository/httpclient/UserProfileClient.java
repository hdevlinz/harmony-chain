package com.tth.identity.repository.httpclient;

import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.customer.CustomerRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-client", url = "${app.services.profile.url}")
public interface UserProfileClient {

    @GetMapping(value = "/internal/profiles/carrier/{userId}")
    CarrierResponse getCarrierProfile(@PathVariable String userId);

    @GetMapping(value = "/internal/profiles/customer/{userId}")
    CustomerResponse getCustomerProfile(@PathVariable String userId);

    @GetMapping(value = "/internal/profiles/supplier/{userId}")
    SupplierResponse getSupplierProfile(@PathVariable String userId);

    @PostMapping(value = "/internal/profiles")
    CarrierResponse createUserProfile(@RequestBody CarrierRequestCreate request);

    @PostMapping(value = "/internal/profiles")
    CustomerResponse createUserProfile(@RequestBody CustomerRequestCreate request);

    @PostMapping(value = "/internal/profiles")
    SupplierResponse createUserProfile(@RequestBody SupplierRequestCreate request);

}
