package com.mahmoud.E_commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
