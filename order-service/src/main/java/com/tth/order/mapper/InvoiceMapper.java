package com.tth.order.mapper;

import com.tth.commonlibrary.dto.response.order.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    @Named("toInvoiceResponse")
    @Mapping(target = "invoiceDate", source = "createdAt")
    InvoiceResponse toInvoiceResponse(Invoice invoice);

}
