package com.mahmoud.E_commerce.dto;

import com.mahmoud.E_commerce.entity.enums.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
