package com.tth.product.controller;

import com.tth.product.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/units", produces = "application/json; charset=UTF-8")
public class APIUnitController {

    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<?> listUnits(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                       @RequestParam(required = false, defaultValue = "1") int page,
                                       @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.unitService.findAll(params, page, size));
    }

}
