package com.mahmoud.E_commerce.controller;

import com.mahmoud.E_commerce.dto.ProductDTO;
import com.mahmoud.E_commerce.dto.ProductListDTO;
import com.mahmoud.E_commerce.entity.Product;
import com.mahmoud.E_commerce.mapper.ProductMapper;
import com.mahmoud.E_commerce.service.ProductService;
import com.mahmoud.E_commerce.utils.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Products")
@RestController
@RequestMapping(path = "/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Everyone can view all products")
    @GetMapping
    public ResponseEntity<GlobalResponse<Page<ProductListDTO>>> getAllProducts(Pageable pageable) {
        Page<ProductListDTO> products = productService.getAllProducts(pageable);
        GlobalResponse<Page<ProductListDTO>> response = new GlobalResponse<>(products);
        response.setMessage("Products retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Everyone can view details of a specific product")
    @GetMapping(path = "/{productId}")
    public ResponseEntity<GlobalResponse<ProductDTO>> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        GlobalResponse<ProductDTO> response = new GlobalResponse<>(productMapper.toDTO(product));
        response.setMessage("Product retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Only admin can create products")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse<ProductDTO>> createProduct(
            @ModelAttribute @Valid ProductDTO productDTO,
            @RequestPart(value = "image", required = false
            ) MultipartFile image) throws IOException {
        Product product = productService.createProduct(productMapper.toEntity(productDTO), image);
        GlobalResponse<ProductDTO> response = new GlobalResponse<>(productMapper.toDTO(product));
        response.setMessage("Product created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Only admin can update products")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GlobalResponse<ProductDTO>> updateProduct(
            @PathVariable Long productId,
            @ModelAttribute ProductDTO productDTO,
            @RequestPart(value = "image", required = false
            ) MultipartFile image) throws IOException {
        Product product = productService.updateProduct(productId, productDTO, image);
        GlobalResponse<ProductDTO> response = new GlobalResponse<>(productMapper.toDTO(product));
        response.setMessage("Product updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Only admin can delete products")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<GlobalResponse<?>> deleteProduct(@PathVariable Long productId) throws IOException {
        productService.deleteProduct(productId);
        GlobalResponse<Void> response = new GlobalResponse<>(null);
        response.setMessage("Product deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
