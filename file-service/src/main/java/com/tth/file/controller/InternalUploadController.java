package com.tth.file.controller;

import com.tth.file.service.S3FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/upload", produces = "application/json; charset=UTF-8")
public class InternalUploadController {

    private final S3FileUploadService s3FileUploadService;

    @PostMapping("/images")
    public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files, @RequestParam String category) {
        List<String> imageUrls = s3FileUploadService.uploadMultiFiles(files, category);

        return ResponseEntity.ok(imageUrls);
    }

}
