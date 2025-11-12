package com.casa_training.casa_task_day_one.presentation.rest.controller;

import com.casa_training.casa_task_day_one.presentation.rest.dto.CreateUserRequest;
import com.casa_training.casa_task_day_one.presentation.rest.dto.CreateUserResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.LoginRequest;
import com.casa_training.casa_task_day_one.presentation.rest.dto.LoginResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import com.casa_training.casa_task_day_one.usecase.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    private ResponseEntity<BaseResponse<LoginResponse>> login(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginResponse loginResponse = userService.login(loginRequest);
        BaseResponse<LoginResponse> response = new BaseResponse<>();
        response.setData(loginResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    private ResponseEntity<BaseResponse<CreateUserResponse>> register(
            @RequestBody CreateUserRequest createUserRequest
    ){
        CreateUserResponse result = userService.createUser(createUserRequest);
        BaseResponse<CreateUserResponse> response = new BaseResponse<>();
        response.setData(result);
        return ResponseEntity.ok(response);
    }
}