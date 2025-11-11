package com.casa_training.casa_task_day_one.presentation.rest.dto.res;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private UUID reqId = UUID.randomUUID();
    private String status = "T";
    private String message = "Berhasil";
    private Object error = null;
    private T data = null;
}
