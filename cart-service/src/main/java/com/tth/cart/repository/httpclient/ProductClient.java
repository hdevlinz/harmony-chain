package com.tth.cart.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "product-client", url = "${app.services.product.url}")
public interface ProductClient {

    @PostMapping(path = "/products/batch")
    APIResponse<List<ProductListResponse>> listProductsInBatch(@RequestBody Set<String> productIds);

    @GetMapping("/products/{productId}")
    APIResponse<ProductDetailsResponse> getProduct(@PathVariable String productId);

}
