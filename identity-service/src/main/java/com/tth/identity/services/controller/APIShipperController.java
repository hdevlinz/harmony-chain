package com.tth.identity.services.controller;

import com.tth.identity.services.dto.response.APIResponse;
import com.tth.identity.services.dto.response.shipper.ShipperResponse;
import com.tth.identity.services.entity.Shipper;
import com.tth.identity.services.mapper.ShipperMapper;
import com.tth.identity.services.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/shippers", produces = "application/json; charset=UTF-8")
public class APIShipperController {

    private final ShipperMapper shipperMapper;
    private final ShipperService shipperService;

    @GetMapping
    public ResponseEntity<?> listShippers(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.shipperService.findAllWithFilter(params, page, size));
    }

    @GetMapping(path = "/{shipperId}")
    public ResponseEntity<?> getShipper(@PathVariable(value = "shipperId") String id) {
        Shipper shipper = this.shipperService.findById(id);
        ShipperResponse result = this.shipperMapper.toShipperResponse(shipper);

        return ResponseEntity.ok(APIResponse.<ShipperResponse>builder().result(result).build());
    }

}
