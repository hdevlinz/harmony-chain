package com.tth.profile.service;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;

public interface UserProfileService {

    CarrierResponse getCarrierProfile(String userId);

    CustomerResponse getCustomerProfile(String userId);

    SupplierResponse getSupplierProfile(String userId);

    UserProfileResponse createUserProfile(UserProfileRequestCreate request);

    UserProfileResponse updateUserProfile();

}
