package com.tth.profile.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.profile.customer.CustomerResponse;
import com.tth.profile.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/customers", produces = "application/json; charset=UTF-8")
public class APICustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> listCustomer(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.customerService.findAll(params, page, size));
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable String customerId) {
        CustomerResponse customer = this.customerService.findById(customerId);

        return ResponseEntity.ok(APIResponse.<CustomerResponse>builder().result(customer).build());
    }

}
