package com.tth.inventory.mapper;

import com.tth.order.dto.response.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {TaxMapper.class})
public interface InvoiceMapper {

    @Named("toInvoiceResponse")
    @Mapping(target = "invoiceDate", source = "createdAt")
    @Mapping(target = "tax", source = "tax", qualifiedByName = "toTaxResponse")
    InvoiceResponse toInvoiceResponse(Invoice invoice);

}
