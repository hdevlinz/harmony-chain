package com.tth.rating.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "profile-service", url = "${app.services.profile.url}")
public interface UserProfileClient {

    @GetMapping("/suppliers")
    PageResponse<SupplierResponse> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                 @RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size);

    @GetMapping(path = "/suppliers/{supplierId}")
    APIResponse<SupplierResponse> getSupplier(@PathVariable String supplierId);

}
