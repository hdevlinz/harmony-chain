package com.tth.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tth.identity.dto.response.customer.CustomerResponse;
import com.tth.identity.dto.response.shipper.ShipperResponse;
import com.tth.identity.dto.response.supplier.SupplierResponse;
import com.tth.identity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    // User
    private String email;
    private String username;
    private String avatar;
    private UserRole role;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastLogin;

    // Instance
    private CustomerResponse customer;
    private ShipperResponse shipper;
    private SupplierResponse supplier;

}
