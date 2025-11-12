package com.casa_training.casa_task_day_one.presentation.rest.controller;

import com.casa_training.casa_task_day_one.presentation.rest.dto.req.CreateUserReqDto;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.BaseResponse;
import com.casa_training.casa_task_day_one.presentation.rest.dto.res.CreateUserResDto;
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
    private UserService userService ;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<CreateUserResDto>> createUser(@RequestBody CreateUserReqDto request) {
        CreateUserResDto result = userService.createUser(request);

        BaseResponse<CreateUserResDto> response = new BaseResponse<>();
        response.setData(result);
        response.setMessage("User berhasil dibuat dengan email: " + result.getEmail());

        return ResponseEntity.ok(response);
    }

}
