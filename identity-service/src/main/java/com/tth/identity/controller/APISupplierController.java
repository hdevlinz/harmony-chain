package com.tth.identity.controller;

import com.tth.identity.dto.response.APIResponse;
import com.tth.identity.dto.response.supplier.SupplierResponse;
import com.tth.identity.entity.Supplier;
import com.tth.identity.mapper.SupplierMapper;
import com.tth.identity.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/suppliers", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierMapper supplierMapper;
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                           @RequestParam(required = false, defaultValue = "1") int page,
                                           @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.supplierService.findAllWithFilter(params, page, size));
    }

    @GetMapping(path = "/{supplierId}")
    public ResponseEntity<?> getSupplier(@PathVariable(value = "supplierId") String id) {
        Supplier supplier = this.supplierService.findById(id);
        SupplierResponse result = this.supplierMapper.toSupplierResponse(supplier);

        return ResponseEntity.ok(APIResponse.<SupplierResponse>builder().result(result).build());
    }

}
