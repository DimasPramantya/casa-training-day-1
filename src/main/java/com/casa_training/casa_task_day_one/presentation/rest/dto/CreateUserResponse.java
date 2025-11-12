package com.casa_training.casa_task_day_one.presentation.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class CreateUserResponse {
    @JsonProperty("name")
    @Getter
    @Setter
    private String name;
    @JsonProperty("email")
    @Getter
    @Setter
    private String email;
    @JsonProperty("userId")
    @Getter
    @Setter
    private String userId;

    public CreateUserResponse(String name, String email, String userId){
        this.name = name;
        this.email = email;
        this.userId = userId;
    }
}