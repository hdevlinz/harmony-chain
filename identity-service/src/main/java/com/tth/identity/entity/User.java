package com.tth.identity.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tth.commonlibrary.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username"),
})
public class User extends BaseEntity implements Serializable {

    @Transient
    @JsonIgnore
    private MultipartFile file;

    @NotNull(message = "{user.email.notNull}")
    @NotBlank(message = "{user.email.notNull}")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;

    @NotNull(message = "{user.username.notNull}")
    @NotBlank(message = "{user.username.notNull}")
    @Size(min = 6, message = "{user.username.size}")
    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String username;

    @NotNull(message = "{user.password.notNull}")
    @NotBlank(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    @Column(nullable = false, length = 300)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.role.notNull}")
    @Column(nullable = false)
    private UserRole role = UserRole.ROLE_CUSTOMER;

    @Column(length = 300)
    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

//    @ManyToMany
//    @JoinTable(
//            name = "user_permission",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
//    private Set<Permission> permissionSet;

}
