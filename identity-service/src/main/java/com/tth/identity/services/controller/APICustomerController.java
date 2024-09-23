package com.tth.identity.services.controller;

import com.tth.identity.services.dto.response.APIResponse;
import com.tth.identity.services.dto.response.customer.CustomerResponse;
import com.tth.identity.services.entity.Customer;
import com.tth.identity.services.mapper.CustomerMapper;
import com.tth.identity.services.service.CustomerService;
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
        return ResponseEntity.ok(this.customerService.findAllWithFilter(params, page, size));
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable(value = "customerId") String id) {
        Customer customer = this.customerService.findById(id);
        CustomerResponse result = this.customerMapper.toCustomerResponse(customer);

        return ResponseEntity.ok(APIResponse.<CustomerResponse>builder().result(result).build());
    }

}
