package com.tth.order.service.impl;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.response.invoice.InvoiceResponse;
import com.tth.order.entity.Invoice;
import com.tth.order.enums.ErrorCode;
import com.tth.order.exception.AppException;
import com.tth.order.mapper.InvoiceMapper;
import com.tth.order.repository.InvoiceRepository;
import com.tth.order.service.InvoiceService;
import com.tth.order.service.specification.InvoiceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    public InvoiceResponse findByInvoiceNumber(String invoiceNumber) {
        return this.invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .map(invoiceMapper::toInvoiceResponse)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND));
    }

    @Override
    public PageResponse<Invoice> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Invoice> specification = InvoiceSpecification.filter(params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Invoice> invoices = this.invoiceRepository.findAll(specification, pageable);

        return PageResponse.of(invoices);
    }

    @Override
    public PageResponse<Invoice> findAllInvoiceOfAuthenticated(Map<String, String> params, int page, int size) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Specification<Invoice> specification = InvoiceSpecification.filter(params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Invoice> invoices = this.invoiceRepository.findAllByUserId(user.getName(), specification, pageable);

        return PageResponse.of(invoices);
    }

}
