package com.tth.order.mapper.helper;

import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.order.repository.httpclient.ProductClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final ProductClient productClient;

    @Named("getProductById")
    public ProductListResponse getProductById(String productId) {
        Set<String> productIds = Set.of(productId);

        return this.productClient.listProductsInBatch(productIds).getResult().getFirst();
    }

}
