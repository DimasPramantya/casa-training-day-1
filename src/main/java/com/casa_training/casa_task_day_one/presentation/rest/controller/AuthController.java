package com.casa_training.casa_task_day_one.presentation.rest.controller;

import com.casa_training.casa_task_day_one.presentation.rest.dto.req.LoginRequest;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.LoginResponse;
import com.casa_training.casa_task_day_one.usecase.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
