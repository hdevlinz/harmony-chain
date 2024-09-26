package com.tth.order.repository.httpclient;

import com.tth.order.dto.APIResponse;
import com.tth.order.dto.PageResponse;
import com.tth.order.dto.response.product.ProductDetailsResponse;
import com.tth.order.dto.response.product.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
    APIResponse<List<ProductListResponse>> getProductsInBatch(@RequestBody Set<String> productIds);

    @GetMapping("/products/{productId}")
    APIResponse<ProductDetailsResponse> getProduct(@PathVariable String productId);

}
