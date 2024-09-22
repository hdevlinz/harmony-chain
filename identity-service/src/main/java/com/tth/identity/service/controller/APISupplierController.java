package com.tth.identity.service.controller;

import com.tth.identity.service.dto.response.APIResponse;
import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.supplier.SupplierResponse;
import com.tth.identity.service.entity.Supplier;
import com.tth.identity.service.mapper.SupplierMapper;
import com.tth.identity.service.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/suppliers", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierMapper supplierMapper;
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                           @RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        PageResponse<SupplierResponse> results = this.supplierService.findAllWithFilter(params, page, size);

        return ResponseEntity.ok(APIResponse.<PageResponse<SupplierResponse>>builder().result(results).build());
    }

    @GetMapping(path = "/{supplierId}")
    public ResponseEntity<?> getSupplier(@PathVariable(value = "supplierId") String id) {
        Supplier supplier = this.supplierService.findById(id);
        SupplierResponse result = this.supplierMapper.toSupplierResponse(supplier);

        return ResponseEntity.ok(APIResponse.<SupplierResponse>builder().result(result).build());
    }
}
