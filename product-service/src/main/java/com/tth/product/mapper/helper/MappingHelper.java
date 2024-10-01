package com.tth.product.mapper.helper;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.product.repository.httpclient.UserProfileClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final UserProfileClient userProfileClient;

    @Named("getSupplierById")
    public SupplierResponse getSupplierById(String supplierId) {
        APIResponse<SupplierResponse> response = this.userProfileClient.getSupplier(supplierId);

        return response != null ? response.getResult() : null;
    }

}
