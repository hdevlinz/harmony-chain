package com.tth.product.controller;

import com.tth.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories", produces = "application/json; charset=UTF-8")
public class APICategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> listCategories(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                            @RequestParam(required = false, defaultValue = "1") int page,
                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.categoryService.findAllWithFilter(params, page, size));
    }

}
