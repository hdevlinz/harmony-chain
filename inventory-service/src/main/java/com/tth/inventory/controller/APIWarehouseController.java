package com.tth.inventory.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestCreate;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestUpdate;
import com.tth.commonlibrary.dto.response.warehouse.WarehouseResponse;
import com.tth.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/warehouses", produces = "application/json; charset=UTF-8")
public class APIWarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<?> listWarehouses(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.warehouseService.findAll(params, page, size));
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody @Valid WarehouseRequestCreate request) {
        WarehouseResponse createdWarehouse = this.warehouseService.create(request);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(createdWarehouse).build());
    }

    @GetMapping(path = "/{warehouseId}")
    public ResponseEntity<?> getWarehouse(@PathVariable String warehouseId) {
        WarehouseResponse warehouse = this.warehouseService.findById(warehouseId);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(warehouse).build());
    }

    @PatchMapping(path = "/{warehouseId}")
    public ResponseEntity<?> updateWarehouse(@PathVariable String warehouseId, @RequestBody @Valid WarehouseRequestUpdate request) {
        WarehouseResponse updatedWarehouse = this.warehouseService.update(warehouseId, request);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(updatedWarehouse).build());
    }

    @DeleteMapping(path = "/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable String warehouseId) {
        this.warehouseService.delete(warehouseId);

        return ResponseEntity.noContent().build();
    }

}
