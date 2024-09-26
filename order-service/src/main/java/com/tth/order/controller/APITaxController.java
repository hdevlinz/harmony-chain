package com.tth.order.controller;

import com.tth.order.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/taxes", produces = "application/json; charset=UTF-8")
public class APITaxController {

    private final TaxService taxService;

    @GetMapping
    public ResponseEntity<?> getTaxes(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.taxService.findAllWithFilter(params, page, size));
    }

}
