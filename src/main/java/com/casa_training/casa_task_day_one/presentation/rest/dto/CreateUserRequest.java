package com.casa_training.casa_task_day_one.presentation.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CreateUserRequest {
    @JsonProperty("name")
    @Getter
    @Setter
    private String name;
    @JsonProperty("email")
    @Getter
    @Setter
    private String email;
    @JsonProperty("password")
    @Getter
    @Setter
    private String password;
}
