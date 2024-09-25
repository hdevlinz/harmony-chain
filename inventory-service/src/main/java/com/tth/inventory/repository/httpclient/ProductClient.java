package com.tth.inventory.repository.httpclient;

import com.tth.inventory.dto.PageResponse;
import com.tth.inventory.dto.response.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "product-client", url = "${app.services.product.url}")
public interface ProductClient {

    @GetMapping(value = "/products")
    PageResponse<ProductListResponse> listProducts(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size);

}
