package com.tth.identity.services.controller;

import com.tth.identity.services.dto.request.RegisterRequest;
import com.tth.identity.services.dto.request.UpdateRequest;
import com.tth.identity.services.dto.response.APIResponse;
import com.tth.identity.services.dto.response.UserResponse;
import com.tth.identity.services.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users", produces = "application/json; charset=UTF-8")
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
