package com.tth.commonlibrary.dto.request.identity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;

    private String oldPassword;

    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String newPassword;

    private MultipartFile avatar;


}
