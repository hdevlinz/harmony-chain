package com.tth.product.controller;

import com.tth.product.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tags", produces = "application/json; charset=UTF-8")
public class APITagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> listTags(@RequestParam(required = false, defaultValue = "") Map<String, String> params,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(this.tagService.findAll(params, page, size));
    }

}
