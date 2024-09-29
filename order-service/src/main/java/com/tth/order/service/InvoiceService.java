package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;

import java.util.Map;

public interface InvoiceService {

    InvoiceResponse findByInvoiceNumber(String invoiceNumber);

    PageResponse<Invoice> findAllWithFilter(Map<String, String> params, int page, int size);

    PageResponse<Invoice> findAllInvoiceOfAuthenticated(Map<String, String> params, int page, int size);

}
