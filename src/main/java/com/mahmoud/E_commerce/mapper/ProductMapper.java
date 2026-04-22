package com.mahmoud.E_commerce.mapper;

import com.mahmoud.E_commerce.dto.ProductDTO;
import com.mahmoud.E_commerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface ProductMapper {
    @Mapping(target = "imageUrl", source = "image")
    ProductDTO toDTO(Product product);

    @Mapping(target = "image", source = "imageUrl")
    Product toEntity(ProductDTO productDTO);
}
