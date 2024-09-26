package com.tth.order.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.tth.order.dto.APIResponse;
import com.tth.order.dto.request.invoice.ChargeRequest;
import com.tth.order.dto.response.invoice.InvoiceResponse;
import com.tth.order.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoices", produces = "application/json; charset=UTF-8")
public class APIInvoiceController {

    private final InvoiceService invoiceService;

    @Value("${stripe.secret-key}")
    private String secretKey;

    @GetMapping
    public ResponseEntity<?> getAllInvoiceOfAuthenticated(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                                          @RequestParam(required = false, defaultValue = "1") int page,
                                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.invoiceService.findAllInvoiceOfAuthenticated(params, page, size));
    }

    @GetMapping(path = "/{invoiceNumber}")
    public ResponseEntity<?> findInvoiceByInvoiceNumber(@PathVariable String invoiceNumber) {
        InvoiceResponse invoice = this.invoiceService.findByInvoiceNumber(invoiceNumber);

        return ResponseEntity.ok(APIResponse.<InvoiceResponse>builder().result(invoice).build());
    }

    @PostMapping(path = "/charge")
    public ResponseEntity<?> charge(@RequestBody @Valid ChargeRequest chargeRequest) throws StripeException {
        Stripe.apiKey = secretKey;
        Map<String, String> response = new HashMap<>();
        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount(Long.parseLong(String.valueOf(chargeRequest.getAmount())))
                .setCurrency("vnd")
                .addPaymentMethodType("card")
                .setReceiptEmail(chargeRequest.getCustomer().getCustomerEmail())
                .setDescription("Hóa đơn thanh toán đơn hàng của khách hàng từ Harmony SCMS")
                .putAllMetadata(Map.of(
                        "customer_name", chargeRequest.getCustomer().getCustomerName(),
                        "customer_email", chargeRequest.getCustomer().getCustomerEmail(),
                        "customer_phone", chargeRequest.getCustomer().getCustomerPhone(),
                        "customer_address", chargeRequest.getCustomer().getCustomerAddress())
                )
                .putMetadata("products", chargeRequest.getProducts().stream()
                        .map(product -> String.format("ID: %s, Name: %s, Price: %s",
                                product.getId(), product.getName(), product.getPrice().toString()))
                        .collect(Collectors.joining("\n"))
                )
                .setPaymentMethodOptions(PaymentIntentCreateParams.PaymentMethodOptions.builder()
                        .setCard(PaymentIntentCreateParams.PaymentMethodOptions.Card.builder()
                                .setRequestThreeDSecure(PaymentIntentCreateParams.PaymentMethodOptions.Card.RequestThreeDSecure.AUTOMATIC)
                                .build())
                        .build())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        response.put("clientSecret", paymentIntent.getClientSecret());

        return ResponseEntity.ok(APIResponse.<Map<String, String>>builder().result(response).build());
    }

}
