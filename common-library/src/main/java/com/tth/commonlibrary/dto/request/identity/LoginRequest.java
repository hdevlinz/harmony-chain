package com.tth.commonlibrary.dto.request.identity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "{user.username.notNull}")
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;

}
