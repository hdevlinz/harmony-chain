package com.tth.commonlibrary.dto.request.profile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.customer.CustomerRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarrierRequestCreate.class, name = "carrier"),
        @JsonSubTypes.Type(value = CustomerRequestCreate.class, name = "customer"),
        @JsonSubTypes.Type(value = SupplierRequestCreate.class, name = "supplier")
})
public abstract class UserProfileRequestCreate {
}
