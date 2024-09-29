package com.tth.inventory.dto.response.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.order.dto.response.tax.TaxResponse;
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
