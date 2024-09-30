package com.tth.product.mapper.helper;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.product.repository.httpclient.UserProfileClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMappingHelper {

    private final UserProfileClient userProfileClient;

    @Named("getSupplier")
    public SupplierResponse getSupplier(String supplierId) {
        APIResponse<SupplierResponse> response = userProfileClient.getSupplier(supplierId);
        return response != null ? response.getResult() : null;
    }

}
