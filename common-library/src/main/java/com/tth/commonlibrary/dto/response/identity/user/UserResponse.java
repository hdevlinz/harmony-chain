package com.tth.commonlibrary.dto.response.identity.user;

import com.tth.commonlibrary.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    protected String id;

    protected Boolean active;

    protected String createdAt;

    protected String updatedAt;

    private String email;

    private String username;

    private String password;

    private UserRole role;

    private String avatar;

    private String lastLogin;

}
