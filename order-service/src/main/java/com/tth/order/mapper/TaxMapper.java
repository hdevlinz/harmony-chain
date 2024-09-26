package com.tth.order.mapper;

import com.tth.order.dto.response.tax.TaxResponse;
import com.tth.order.entity.Tax;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    @Named("toTaxResponse")
    TaxResponse toTaxResponse(Tax tax);

}
