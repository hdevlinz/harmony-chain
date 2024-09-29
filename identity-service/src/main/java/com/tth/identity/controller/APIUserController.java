package com.tth.identity.controller;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.dto.request.identity.UpdateRequest;
import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.identity.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", produces = "application/json; charset=UTF-8")
public class APIUserController {

    private final UserService userService;

    @PostMapping(path = "/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegisterRequest request) {
        UserResponse result = this.userService.registration(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.<UserResponse>builder().result(result).build());
    }

    @GetMapping(path = "/info")
    public ResponseEntity<?> getInfo() {
        UserResponse result = this.userService.getInfo();

        return ResponseEntity.ok(APIResponse.<UserResponse>builder().result(result).build());
    }

    @PatchMapping(path = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateInfo(@ModelAttribute @Valid UpdateRequest request) {
        UserResponse result = this.userService.updateInfo(request);

        return ResponseEntity.ok(APIResponse.<UserResponse>builder().result(result).build());
    }

}
