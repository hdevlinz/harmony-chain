package com.tth.order.dto.response.user;

import com.tth.order.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

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
