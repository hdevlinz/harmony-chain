package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InvoiceMapper {

    @Named("toInvoiceResponse")
    @Mapping(target = "invoiceDate", source = "createdAt")
    InvoiceResponse toInvoiceResponse(Invoice invoice);

}
