package com.tth.identity.controller.internal;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.identity.entity.User;
import com.tth.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/users", produces = "application/json; charset=UTF-8")
public class InternalAPIUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(APIResponse.<List<User>>builder().result(this.userService.findAll()).build());
    }

    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(APIResponse.<User>builder().result(this.userService.findById(userId)).build());
    }

}
