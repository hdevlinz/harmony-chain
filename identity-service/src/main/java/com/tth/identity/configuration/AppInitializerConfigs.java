package com.tth.identity.configuration;

import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.enums.UserRole;
import com.tth.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppInitializerConfigs {

    @Bean
    public ApplicationRunner applicationRunner(UserService userService) {
        return args -> {
            log.info("Initializing application.....");

            if (!userService.existsByUsername("adminscm")) {
                userService.registration(RegisterRequest.builder()
                        .email("admin@yopmail.com")
                        .username("adminscm")
                        .password("adminscm")
                        .role(UserRole.ROLE_ADMIN)
                        .build());

                this.createCustomer(userService);
                this.createCarrier(userService);
                this.createSupplier(userService);
            }

            log.info("Application initialization completed.....");
        };
    }

    private void createCustomer(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("customer1@yopmail.com")
                .username("customer1")
                .password("customer1")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("Hiep")
                .customerMiddleName("Thanh")
                .customerLastName("Tran")
                .customerAddress("TPHCM")
                .customerPhone("0123456789")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer2@yopmail.com")
                .username("customer2")
                .password("customer2")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("Chi")
                .customerMiddleName("Thi Minh")
                .customerLastName("Nguyen")
                .customerAddress("TPHCM")
                .customerPhone("9872635196")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer3@yopmail.com")
                .username("customer3")
                .password("customer3")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("Hau")
                .customerMiddleName("Thanh")
                .customerLastName("Hau")
                .customerAddress("TPHCM")
                .customerPhone("2781764019")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer4@yopmail.com")
                .username("customer4")
                .password("customer4")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("Hien")
                .customerMiddleName("Thi")
                .customerLastName("Doan")
                .customerAddress("TPHCM")
                .customerPhone("2781236019")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer5@yopmail.com")
                .username("customer5")
                .password("customer5")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("Hung")
                .customerMiddleName("Thanh")
                .customerLastName("Tran")
                .customerAddress("TPHCM")
                .customerPhone("2781764059")
                .build());
    }

    private void createCarrier(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("carrier1@yopmail.com")
                .username("carrier1")
                .password("carrier1")
                .role(UserRole.ROLE_CARRIER)
                .carrierName("Carrier 1")
                .carrierContactInfo("0987654321")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("carrier2@yopmail.com")
                .username("carrier2")
                .password("carrier2")
                .role(UserRole.ROLE_CARRIER)
                .carrierName("Carrier 2")
                .carrierContactInfo("8239184751")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("carrier3@yopmail.com")
                .username("carrier3")
                .password("carrier3")
                .role(UserRole.ROLE_CARRIER)
                .carrierName("Carrier 3")
                .carrierContactInfo("2617384928")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("carrier4@yopmail.com")
                .username("carrier4")
                .password("carrier4")
                .role(UserRole.ROLE_CARRIER)
                .carrierName("Carrier 4")
                .carrierContactInfo("2617324928")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("carrier5@yopmail.com")
                .username("carrier5")
                .password("carrier5")
                .role(UserRole.ROLE_CARRIER)
                .carrierName("Carrier 5")
                .carrierContactInfo("2117324928")
                .build());
    }

    private void createSupplier(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("supplier1@yopmail.com")
                .username("supplier1")
                .password("supplier1")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 1")
                .supplierAddress("TPHCM")
                .supplierPhone("1234567890")
                .supplierContactInfo("1234567890")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier2@yopmail.com")
                .username("supplier2")
                .password("supplier2")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 2")
                .supplierAddress("TPHCM")
                .supplierPhone("5982716231")
                .supplierContactInfo("5982716231")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier3@yopmail.com")
                .username("supplier3")
                .password("supplier3")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 3")
                .supplierAddress("TPHCM")
                .supplierPhone("6782910498")
                .supplierContactInfo("6782910498")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier4@yopmail.com")
                .username("supplier4")
                .password("supplier4")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 4")
                .supplierAddress("TPHCM")
                .supplierPhone("6782910498")
                .supplierContactInfo("6782910498")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier5@yopmail.com")
                .username("supplier5")
                .password("supplier5")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 5")
                .supplierAddress("TPHCM")
                .supplierPhone("6782910498")
                .supplierContactInfo("6782910498")
                .build());
    }

}
