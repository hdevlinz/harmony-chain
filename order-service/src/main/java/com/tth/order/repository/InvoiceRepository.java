package com.tth.order.repository;

import com.tth.order.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String>, JpaSpecificationExecutor<Invoice> {

    Optional<Invoice> findByUserIdAndInvoiceNumber(String userId, String invoiceNumber);

    Optional<Invoice> findByOrderId(String orderId);

}
