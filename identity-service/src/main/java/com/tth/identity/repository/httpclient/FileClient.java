package com.tth.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file-client", url = "${app.services.file.url}")
public interface FileClient {

    @PostMapping(value = "/internal/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> uploadImages(@RequestPart("images") List<MultipartFile> files, @RequestParam("category") String category);

}
