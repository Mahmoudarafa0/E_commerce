package com.mahmoud.E_commerce.mapper;

import com.mahmoud.E_commerce.dto.CartItemDTO;
import com.mahmoud.E_commerce.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    @Mapping(target = "productId", source = "product.id")
    CartItemDTO toDTO(CartItem cartItem);

    @Mapping(target = "product.id", source = "productId")
    CartItem toEntity(CartItemDTO cartItemDTO);
}
