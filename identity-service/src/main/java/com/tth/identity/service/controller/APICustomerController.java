package com.tth.identity.service.controller;

import com.tth.identity.service.dto.response.APIResponse;
import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.customer.CustomerResponse;
import com.tth.identity.service.entity.Customer;
import com.tth.identity.service.mapper.CustomerMapper;
import com.tth.identity.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/customers", produces = "application/json; charset=UTF-8")
public class APICustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> listCustomer(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        PageResponse<CustomerResponse> results = this.customerService.findAllWithFilter(params, page, size);

        return ResponseEntity.ok(APIResponse.<PageResponse<CustomerResponse>>builder().result(results).build());
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable(value = "customerId") String id) {
        Customer customer = this.customerService.findById(id);
        CustomerResponse result = this.customerMapper.toCustomerResponse(customer);

        return ResponseEntity.ok(APIResponse.<CustomerResponse>builder().result(result).build());
    }
}
