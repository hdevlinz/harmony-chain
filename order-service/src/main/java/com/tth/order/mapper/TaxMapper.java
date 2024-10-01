package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.tax.TaxResponse;
import com.tth.order.entity.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    @Named("toTaxResponse")
    TaxResponse toTaxResponse(Tax tax);

}
