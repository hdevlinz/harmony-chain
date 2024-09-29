package com.tth.profile.controller;

import com.tth.profile.dto.APIResponse;
import com.tth.profile.dto.response.supplier.SupplierResponse;
import com.tth.profile.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/suppliers", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                           @RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.supplierService.findAllWithFilter(params, page, size));
    }

    @GetMapping(path = "/{supplierId}")
    public ResponseEntity<?> getSupplier(@PathVariable String supplierId) {
        SupplierResponse supplier = this.supplierService.findById(supplierId);

        return ResponseEntity.ok(APIResponse.<SupplierResponse>builder().result(supplier).build());
    }

}
