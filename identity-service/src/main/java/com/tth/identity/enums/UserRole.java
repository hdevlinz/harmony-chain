package com.tth.identity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserRole {

    ROLE_ADMIN("Quản trị viên"),
    ROLE_CUSTOMER("Khách hàng"),
    ROLE_DISTRIBUTOR("Nhà phân phối"),
    ROLE_MANUFACTURER("Nhà sản xuất"),
    ROLE_SHIPPER("Nhà vận chuyển"),
    ROLE_SUPPLIER("Nhà cung cấp");

    private final String displayName;

    public static Map<String, String> getAllDisplayNames() {
        return Arrays.stream(UserRole.values())
                .collect(Collectors.toMap(UserRole::name, UserRole::getDisplayName));
    }

    public String alias() {
        return this.name().substring(5);
    }

}
