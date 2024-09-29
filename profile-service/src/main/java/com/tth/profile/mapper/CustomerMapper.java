package com.tth.profile.mapper;

import com.tth.identity.dto.request.RegisterRequest;
import com.tth.identity.dto.request.UpdateRequest;
import com.tth.profile.dto.response.customer.CustomerResponse;
import com.tth.profile.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "firstName", source = "customerFirstName")
    @Mapping(target = "middleName", source = "customerMiddleName")
    @Mapping(target = "lastName", source = "customerLastName")
    @Mapping(target = "address", source = "customerAddress")
    @Mapping(target = "phone", source = "customerPhone")
    Customer toCustomer(RegisterRequest registerRequest);

    @Named("mapCustomerToResponse")
    CustomerResponse toCustomerResponse(Customer customer);

    List<CustomerResponse> toCustomerResponse(List<Customer> customers);

    @Mapping(target = "firstName", source = "customerFirstName")
    @Mapping(target = "middleName", source = "customerMiddleName")
    @Mapping(target = "lastName", source = "customerLastName")
    @Mapping(target = "address", source = "customerAddress")
    @Mapping(target = "phone", source = "customerPhone")
    @Mapping(target = "gender", source = "customerGender")
    @Mapping(target = "dateOfBirth", source = "customerDateOfBirth")
    void updateCustomer(@MappingTarget Customer customer, UpdateRequest updateRequest);

}
