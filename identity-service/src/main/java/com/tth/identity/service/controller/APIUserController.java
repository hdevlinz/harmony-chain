package com.tth.identity.service.controller;

import com.tth.identity.service.dto.request.user.RegisterRequest;
import com.tth.identity.service.dto.request.user.UpdateRequest;
import com.tth.identity.service.dto.response.APIResponse;
import com.tth.identity.service.dto.response.user.UserResponse;
import com.tth.identity.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users", produces = "application/json; charset=UTF-8")
public class APIUserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) {
        UserResponse result = this.userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.<UserResponse>builder().result(result).build());
    }

    @GetMapping(path = "/me")
    public ResponseEntity<?> getInfo() {
        UserResponse result = this.userService.getInfo();

        return ResponseEntity.ok(APIResponse.<UserResponse>builder().result(result).build());
    }

    @PatchMapping(path = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateInfo(@ModelAttribute @Valid UpdateRequest request, BindingResult bindingResult) {
        UserResponse result = this.userService.updateInfo(request);

        return ResponseEntity.ok(APIResponse.<UserResponse>builder().result(result).build());
    }
}
