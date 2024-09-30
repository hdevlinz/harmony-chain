package com.tth.commonlibrary.dto.response.order.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.commonlibrary.dto.response.order.tax.TaxResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

    private String id;

    private String invoiceNumber;

    private Boolean paid;

    private BigDecimal totalAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime invoiceDate;

    private TaxResponse tax;

}
