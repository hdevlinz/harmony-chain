package com.tth.order.controller.crud;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.warehouse.CreateWarehouseRequest;
import com.tth.commonlibrary.dto.request.warehouse.UpdateWarehouseRequest;
import com.tth.commonlibrary.dto.response.warehouse.WarehouseResponse;
import com.tth.order.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/warehouse", produces = "application/json; charset=UTF-8")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<?> listWarehouses(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.warehouseService.findAllWithFilter(params, page, size));
    }

    @PostMapping
    public ResponseEntity<?> createWarehouse(@RequestBody @Valid CreateWarehouseRequest request) {
        WarehouseResponse createdWarehouse = this.warehouseService.create(request);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(createdWarehouse).build());
    }

    @GetMapping(path = "/{warehouseId}")
    public ResponseEntity<?> getWarehouse(@PathVariable String warehouseId) {
        WarehouseResponse warehouse = this.warehouseService.findById(warehouseId);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(warehouse).build());
    }

    @PatchMapping(path = "/{warehouseId}")
    public ResponseEntity<?> updateWarehouse(@PathVariable String warehouseId, @RequestBody @Valid UpdateWarehouseRequest request) {
        WarehouseResponse updatedWarehouse = this.warehouseService.update(warehouseId, request);

        return ResponseEntity.ok(APIResponse.<WarehouseResponse>builder().result(updatedWarehouse).build());
    }

    @DeleteMapping(path = "/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable String warehouseId) {
        this.warehouseService.delete(warehouseId);

        return ResponseEntity.noContent().build();
    }

}
