package com.mahmoud.E_commerce.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GlobalResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;


    public GlobalResponse(T data) {
        this.success = true;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
