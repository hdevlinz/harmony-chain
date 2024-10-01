package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.tax.TaxResponse;
import com.tth.order.entity.Tax;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TaxMapper {

    @Named("toTaxResponse")
    TaxResponse toTaxResponse(Tax tax);

}
