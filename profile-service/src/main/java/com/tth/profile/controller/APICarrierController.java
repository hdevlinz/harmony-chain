package com.tth.profile.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.profile.service.CarrierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/carriers", produces = "application/json; charset=UTF-8")
public class APICarrierController {

    private final CarrierService carrierService;

    @GetMapping
    public ResponseEntity<?> listCarriers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.carrierService.findAll(params, page, size));
    }

    @GetMapping(path = "/{carrierId}")
    public ResponseEntity<?> getCarrier(@PathVariable String carrierId) {
        CarrierResponse shipper = this.carrierService.findById(carrierId);

        return ResponseEntity.ok(APIResponse.<CarrierResponse>builder().result(shipper).build());
    }

}
