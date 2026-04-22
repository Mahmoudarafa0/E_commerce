package com.mahmoud.E_commerce.service;

import com.mahmoud.E_commerce.dto.ProductDTO;
import com.mahmoud.E_commerce.dto.ProductListDTO;
import com.mahmoud.E_commerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    Page<ProductListDTO> getAllProducts(Pageable pageable);
    Product getProductById(Long id);
    Product createProduct(Product product, MultipartFile image) throws IOException;
    Product updateProduct(Long id, ProductDTO productDTO, MultipartFile image) throws IOException;
    void deleteProduct(Long id) throws IOException;

}
