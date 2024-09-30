package com.tth.profile.mapper;

import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestUpdate;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.profile.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    Supplier toSupplier(SupplierRequestCreate request);

    @Named("mapSupplierToResponse")
    SupplierResponse toSupplierResponse(Supplier supplier);

    List<SupplierResponse> toSupplierResponse(List<Supplier> suppliers);

    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    void updateSupplier(@MappingTarget Supplier supplier, SupplierRequestUpdate request);

}
