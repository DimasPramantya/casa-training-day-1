package com.casa_training.casa_task_day_one.presentation.rest.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    @JsonProperty("token")
    private String token;

    public LoginResponse(String token){
        this.token = token;
    }
}
