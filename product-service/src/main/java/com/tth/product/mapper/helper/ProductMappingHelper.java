package com.tth.product.mapper.helper;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.supplier.SupplierResponse;
import com.tth.product.repository.httpclient.IdentityClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMappingHelper {

    private final IdentityClient identityClient;

    @Named("getSupplier")
    public SupplierResponse getSupplier(String supplierId) {
        APIResponse<SupplierResponse> response = identityClient.getSupplier(supplierId);
        return response != null ? response.getResult() : null;
    }

}
