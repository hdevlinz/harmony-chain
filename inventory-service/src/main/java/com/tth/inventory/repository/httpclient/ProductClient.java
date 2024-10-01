package com.tth.inventory.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "product-client", url = "${app.services.product.url}")
public interface ProductClient {

    @GetMapping(value = "/products")
    PageResponse<ProductListResponse> listProducts(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                   @RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size);

    @PostMapping(path = "/products/batch")
    APIResponse<List<ProductListResponse>> listProductsInBatch(@RequestBody Set<String> productIds);

}
