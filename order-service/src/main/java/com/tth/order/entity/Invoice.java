package com.tth.order.entity;

import com.tth.commonlibrary.utils.GeneratorUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice", indexes = {
        @Index(name = "invoice_number_index", columnList = "invoice_number", unique = true),
})
public class Invoice extends BaseEntity implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotBlank(message = "{invoice.invoiceNumber.notNull}")
    @NotNull(message = "{invoice.invoiceNumber.notNull}")
    @Column(name = "invoice_number", nullable = false, unique = true, length = 36, updatable = false)
    private String invoiceNumber;

    @Builder.Default
    @NotNull(message = "{invoice.isPaid.notNull}")
    @Column(name = "is_paid", nullable = false, columnDefinition = "boolean default false")
    private Boolean paid = false;

    @Builder.Default
    @NotNull(message = "{invoice.totalAmount.notNull}")
    @Column(name = "total_amount", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "tax_id", referencedColumnName = "id")
    private Tax tax;

    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    public void prePersist() {
        super.prePersist();

        if (this.invoiceNumber == null) {
            this.invoiceNumber = GeneratorUtils.generateInvoiceNumber();
        }
    }

}
