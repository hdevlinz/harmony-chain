package com.tth.identity.controller;

import com.tth.identity.dto.APIResponse;
import com.tth.identity.dto.response.shipper.ShipperResponse;
import com.tth.identity.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/shippers", produces = "application/json; charset=UTF-8")
public class APIShipperController {

    private final ShipperService shipperService;

    @GetMapping
    public ResponseEntity<?> listShippers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.shipperService.findAllWithFilter(params, page, size));
    }

    @GetMapping(path = "/{shipperId}")
    public ResponseEntity<?> getShipper(@PathVariable String shipperId) {
        ShipperResponse shipper = this.shipperService.findById(shipperId);

        return ResponseEntity.ok(APIResponse.<ShipperResponse>builder().result(shipper).build());
    }

}
