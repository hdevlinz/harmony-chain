package com.tth.identity.services.configuration;

import com.tth.identity.services.dto.request.RegisterRequest;
import com.tth.identity.services.enums.UserRole;
import com.tth.identity.services.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationInitializerConfigs {

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mysql.cj.jdbc.Driver")
    public ApplicationRunner applicationRunner(UserService userService) {
        return args -> {
            log.info("Initializing application.....");

            if (!userService.existsByUsername("adminscm")) {
                userService.registration(RegisterRequest.builder()
                        .email("admin@scm.com")
                        .username("adminscm")
                        .password("adminscm")
                        .role(UserRole.ROLE_ADMIN)
                        .build());

                this.createCustomer(userService);
                this.createShipper(userService);
                this.createSupplier(userService);
            }

            log.info("Application initialization completed.....");
        };
    }

    private void createCustomer(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("customer1@scm.com")
                .username("customer1")
                .password("customer1")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("First shipperName 1")
                .customerMiddleName("Middle shipperName 1")
                .customerLastName("Last shipperName 1")
                .customerAddress("TPHCM")
                .customerPhone("0123456789")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer2@scm.com")
                .username("customer2")
                .password("customer2")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("First shipperName 2")
                .customerMiddleName("Middle shipperName 2")
                .customerLastName("Last shipperName 2")
                .customerAddress("TPHCM")
                .customerPhone("9872635196")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer3@scm.com")
                .username("customer3")
                .password("customer3")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("First shipperName 3")
                .customerMiddleName("Middle shipperName 3")
                .customerLastName("Last shipperName 3")
                .customerAddress("TPHCM")
                .customerPhone("2781764019")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer4@scm.com")
                .username("customer4")
                .password("customer4")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("First shipperName 4")
                .customerMiddleName("Middle shipperName 4")
                .customerLastName("Last shipperName 4")
                .customerAddress("TPHCM")
                .customerPhone("2781236019")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("customer5@scm.com")
                .username("customer5")
                .password("customer5")
                .role(UserRole.ROLE_CUSTOMER)
                .customerFirstName("First shipperName 5")
                .customerMiddleName("Middle shipperName 5")
                .customerLastName("Last shipperName 5")
                .customerAddress("TPHCM")
                .customerPhone("2781764059")
                .build());
    }

    private void createShipper(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("shipper1@scm.com")
                .username("shipper1")
                .password("shipper1")
                .role(UserRole.ROLE_SHIPPER)
                .shipperName("Shipper 1")
                .shipperContactInfo("0987654321")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("shipper2@scm.com")
                .username("shipper2")
                .password("shipper2")
                .role(UserRole.ROLE_SHIPPER)
                .shipperName("Shipper 2")
                .shipperContactInfo("8239184751")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("shipper3@scm.com")
                .username("shipper3")
                .password("shipper3")
                .role(UserRole.ROLE_SHIPPER)
                .shipperName("Shipper 3")
                .shipperContactInfo("2617384928")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("shipper4@scm.com")
                .username("shipper4")
                .password("shipper4")
                .role(UserRole.ROLE_SHIPPER)
                .shipperName("Shipper 4")
                .shipperContactInfo("2617324928")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("shipper5@scm.com")
                .username("shipper5")
                .password("shipper5")
                .role(UserRole.ROLE_SHIPPER)
                .shipperName("Shipper 5")
                .shipperContactInfo("2117324928")
                .build());
    }

    private void createSupplier(UserService userService) {
        userService.registration(RegisterRequest.builder()
                .email("supplier1@scm.com")
                .username("supplier1")
                .password("supplier1")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 1")
                .supplierAddress("TPHCM")
                .supplierPhone("1234567890")
                .supplierContactInfo("1234567890")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier2@scm.com")
                .username("supplier2")
                .password("supplier2")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 2")
                .supplierAddress("TPHCM")
                .supplierPhone("5982716231")
                .supplierContactInfo("5982716231")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier3@scm.com")
                .username("supplier3")
                .password("supplier3")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 3")
                .supplierAddress("TPHCM")
                .supplierPhone("6782910498")
                .supplierContactInfo("6782910498")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier4@scm.com")
                .username("supplier4")
                .password("supplier4")
                .role(UserRole.ROLE_SUPPLIER)
                .supplierName("Supplier 4")
                .supplierAddress("TPHCM")
                .supplierPhone("6782910498")
                .supplierContactInfo("6782910498")
                .build());
        userService.registration(RegisterRequest.builder()
                .email("supplier5@scm.com")
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
