package com.tth.order.service;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.response.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;

import java.util.Map;

public interface InvoiceService {

    InvoiceResponse findByInvoiceNumber(String invoiceNumber);

    PageResponse<Invoice> findAllWithFilter(Map<String, String> params, int page, int size);

    PageResponse<Invoice> findAllInvoiceOfAuthenticated(Map<String, String> params, int page, int size);

}
