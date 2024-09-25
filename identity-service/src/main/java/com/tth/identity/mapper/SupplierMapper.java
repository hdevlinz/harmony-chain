package com.tth.identity.mapper;

import com.tth.identity.dto.request.RegisterRequest;
import com.tth.identity.dto.request.UpdateRequest;
import com.tth.identity.dto.response.supplier.SupplierResponse;
import com.tth.identity.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    Supplier toSupplier(RegisterRequest registerRequest);

    @Named("mapSupplierToResponse")
    SupplierResponse toSupplierResponse(Supplier supplier);

    List<SupplierResponse> toSupplierResponse(List<Supplier> suppliers);

    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    void updateSupplier(@MappingTarget Supplier supplier, UpdateRequest updateRequest);

}
