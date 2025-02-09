package com.bookmanagement.bookmanagement.controller;

import com.bookmanagement.bookmanagement.dto.user.UserRequest;
import com.bookmanagement.bookmanagement.dto.user.UserResponse;
import com.bookmanagement.bookmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 사용자 등록 API
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(userService.addUser(request));
    }
}