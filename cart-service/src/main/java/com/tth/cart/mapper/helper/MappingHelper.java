package com.tth.cart.mapper.helper;

import com.tth.cart.repository.httpclient.ProductClient;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final ProductClient productClient;

    @Named("getProduct")
    public ProductListResponse getProduct(String productId) {
        if (productId == null) {
            return null;
        }

        Set<String> productIds = Set.of(productId);
        List<ProductListResponse> products = productClient.listProductsInBatch(productIds).getResult();

        if (products.isEmpty()) {
            return null;
        }

        return products.getFirst();
    }

}
