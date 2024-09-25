package com.tth.product.repository.httpclient;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.supplier.SupplierResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "identity-client", url = "${app.services.identity.url}")
public interface IdentityClient {

    @GetMapping(value = "/suppliers")
    PageResponse<SupplierResponse> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                 @RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size);

}
