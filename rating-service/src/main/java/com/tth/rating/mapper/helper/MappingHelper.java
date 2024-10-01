package com.tth.rating.mapper.helper;

import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.rating.repository.httpclient.IdentityClient;
import com.tth.rating.repository.httpclient.UserProfileClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final IdentityClient identityClient;
    private final UserProfileClient profileClient;

    @Named("getUserResponseByUserId")
    public UserResponse getUserResponseByUserId(String userId) {
        com.tth.commonlibrary.dto.response.identity.user.UserResponse userResponse = this.identityClient.getUser(userId).getResult();

        LocalDateTime lastLogin = null;
        try {
            lastLogin = LocalDateTime.parse(userResponse.getLastLogin());
        } catch (Exception ignored) {
        }

        return UserResponse.builder()
                .email(userResponse.getEmail())
                .username(userResponse.getUsername())
                .avatar(userResponse.getAvatar())
                .role(userResponse.getRole())
                .lastLogin(lastLogin)
                .build();
    }

    @Named("getSupplierResponseBySupplierId")
    public SupplierResponse getSupplierResponseBySupplierId(String supplierId) {
        return this.profileClient.getSupplier(supplierId).getResult();
    }

}
