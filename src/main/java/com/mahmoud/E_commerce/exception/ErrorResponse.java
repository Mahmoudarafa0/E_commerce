package com.mahmoud.E_commerce.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    @Builder.Default
    private boolean success = false;

    private String message;

    private List<FieldError> errors;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    @Getter
    @AllArgsConstructor
    @Builder
    public static class FieldError {
        private String field;
        private String error;
    }
}
