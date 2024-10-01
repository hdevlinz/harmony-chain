package com.tth.order.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.order.invoice.InvoiceResponse;

import java.util.Map;

public interface InvoiceService {

    InvoiceResponse findByInvoiceNumber(String invoiceNumber);

    PageResponse<InvoiceResponse> findAllInvoiceOfAuthenticated(Map<String, String> params, int page, int size);

}
