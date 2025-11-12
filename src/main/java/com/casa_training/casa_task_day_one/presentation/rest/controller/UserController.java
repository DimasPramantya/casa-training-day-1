package com.casa_training.casa_task_day_one.presentation.rest.controller;

import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.GetUserResponse;
import com.casa_training.casa_task_day_one.usecase.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<BaseResponse<GetUserResponse>> getProfile(Principal principal) {
        GetUserResponse result = userService.getUser(principal.getName());
        BaseResponse<GetUserResponse> response = new BaseResponse<>();
        response.setData(result);
        return ResponseEntity.ok(response);
    }
}
