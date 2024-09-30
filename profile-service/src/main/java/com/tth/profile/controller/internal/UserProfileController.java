package com.tth.profile.controller.internal;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import com.tth.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/profiles", produces = "application/json; charset=UTF-8")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping(path = "/carrier/{userId}")
    public ResponseEntity<?> getCarrierProfile(@PathVariable String userId) {
        return ResponseEntity.ok(this.userProfileService.getCarrierProfile(userId));
    }

    @GetMapping(path = "/customer/{userId}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable String userId) {
        return ResponseEntity.ok(this.userProfileService.getCustomerProfile(userId));
    }

    @GetMapping(path = "/supplier/{userId}")
    public ResponseEntity<?> getSupplierProfile(@PathVariable String userId) {
        return ResponseEntity.ok(this.userProfileService.getSupplierProfile(userId));
    }

    @PostMapping
    public ResponseEntity<?> createUserProfile(@RequestBody UserProfileRequestCreate request) {
        return ResponseEntity.ok(this.userProfileService.createUserProfile(request));
    }

}
