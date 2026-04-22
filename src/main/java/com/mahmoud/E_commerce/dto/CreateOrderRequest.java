package com.mahmoud.E_commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
}
