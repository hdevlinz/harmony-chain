package com.tth.identity.controller.internal;

import com.tth.identity.dto.APIResponse;
import com.tth.identity.entity.User;
import com.tth.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/internal/users", produces = "application/json; charset=UTF-8")
public class InternalAPIUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(APIResponse.<List<User>>builder().result(this.userService.findAll()).build());
    }

}
