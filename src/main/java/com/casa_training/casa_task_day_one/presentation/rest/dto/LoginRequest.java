package com.casa_training.casa_task_day_one.presentation.rest.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
