package com.casa_training.casa_task_day_one.presentation.rest.dto.res;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetUserResponse {
    private String name;
    private String email;
    private String userId;
    private List<String> authorities;
}