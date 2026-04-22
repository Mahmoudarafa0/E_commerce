package com.mahmoud.E_commerce.mapper;

import com.mahmoud.E_commerce.dto.CartDTO;
import com.mahmoud.E_commerce.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CartItemMapper.class)
public interface CartMapper {
    @Mapping(target = "userId", source = "id")
    CartDTO toDTO(Cart cart);

    @Mapping(target = "id", source = "userId")
    Cart toEntity(CartDTO cartDTO);
}
