package com.tth.identity.mapper;

import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.dto.request.identity.UpdateRequest;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.customer.CustomerRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.identity.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "profile", ignore = true)
    UserResponse toUserResponse(User user);

    @Mapping(target = "avatar", ignore = true)
    void updateUser(@MappingTarget User user, UpdateRequest updateRequest);

    @Mapping(target = "userId", ignore = true)
    CarrierRequestCreate toCarrierRequestCreate(RegisterRequest registerRequest);

    @Mapping(target = "userId", ignore = true)
    CustomerRequestCreate toCustomerRequestCreate(RegisterRequest registerRequest);

    @Mapping(target = "userId", ignore = true)
    SupplierRequestCreate toSupplierRequestCreate(RegisterRequest registerRequest);

}
