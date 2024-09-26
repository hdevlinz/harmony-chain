package com.tth.identity.mapper;

import com.tth.identity.dto.request.RegisterRequest;
import com.tth.identity.dto.request.UpdateRequest;
import com.tth.identity.dto.response.UserResponse;
import com.tth.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        CustomerMapper.class,
        ShipperMapper.class,
        SupplierMapper.class
})
public interface UserMapper {

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "shipper", ignore = true)
    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "customer", source = "customer", qualifiedByName = "mapCustomerToResponse")
    @Mapping(target = "shipper", source = "shipper", qualifiedByName = "mapShipperToResponse")
    @Mapping(target = "supplier", source = "supplier", qualifiedByName = "mapSupplierToResponse")
    UserResponse toUserResponse(User user);

    @Mapping(target = "avatar", ignore = true)
    void updateUser(@MappingTarget User user, UpdateRequest updateRequest);

}
