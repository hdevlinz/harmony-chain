package com.tth.identity.mapper;

import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.dto.request.identity.UpdateRequest;
import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User toUser(RegisterRequest registerRequest);

    UserResponse toUserResponse(User user);

    @Mapping(target = "avatar", ignore = true)
    void updateUser(@MappingTarget User user, UpdateRequest updateRequest);

}
