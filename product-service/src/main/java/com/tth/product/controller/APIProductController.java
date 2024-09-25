package com.tth.product.controller;

import com.tth.product.entity.Product;
import com.tth.product.mapper.ProductMapper;
import com.tth.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/products", produces = "application/json; charset=UTF-8")
public class APIProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<?> listProducts(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                          @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.productService.findAllWithFilter(params, page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(value = "productId") String productId) {
        Product product = this.productService.findById(productId);

        return ResponseEntity.ok(this.productMapper.toProductDetailsResponse(product));
    }

}
