package com.tth.profile.mapper;

import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.supplier.SupplierRequestUpdate;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.profile.entity.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SupplierMapper {

    @Named("toSupplier")
    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    Supplier toSupplier(SupplierRequestCreate request);

    @Named("toSupplierResponse")
    SupplierResponse toSupplierResponse(Supplier supplier);

    @Named("updateSupplier")
    @Mapping(target = "name", source = "supplierName")
    @Mapping(target = "address", source = "supplierAddress")
    @Mapping(target = "phone", source = "supplierPhone")
    @Mapping(target = "contactInfo", source = "supplierContactInfo")
    void updateSupplier(@MappingTarget Supplier supplier, SupplierRequestUpdate request);

}
