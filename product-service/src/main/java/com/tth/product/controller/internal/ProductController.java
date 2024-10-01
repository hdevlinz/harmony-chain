package com.tth.product.controller.internal;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/products", produces = "application/json; charset=UTF-8")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/batch")
    public ResponseEntity<?> listProductsInBatch(@RequestBody Set<String> productIds) {
        List<ProductListResponse> products = this.productService.findAllInBatch(productIds);

        return ResponseEntity.ok(APIResponse.<List<ProductListResponse>>builder().result(products).build());
    }

}
