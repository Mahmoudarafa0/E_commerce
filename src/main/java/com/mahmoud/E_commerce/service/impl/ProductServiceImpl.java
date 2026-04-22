package com.mahmoud.E_commerce.service.impl;

import com.mahmoud.E_commerce.dto.ProductDTO;
import com.mahmoud.E_commerce.dto.ProductListDTO;
import com.mahmoud.E_commerce.entity.Product;
import com.mahmoud.E_commerce.repository.ProductRepository;
import com.mahmoud.E_commerce.service.ProductService;
import com.mahmoud.E_commerce.utils.Helper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final Helper helper;

    @Override
    public Page<ProductListDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAllWithoutComments(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Transactional
    @Override
    public Product createProduct(Product product, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String fileName = helper.saveImage(image);
            product.setImage(fileName);
        }
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, ProductDTO productDTO, MultipartFile image) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        if (image != null && !image.isEmpty()) {
            String oldImage = product.getImage();
            String newImage = image.getOriginalFilename();
            boolean isSameImage = false;
            if (oldImage != null && newImage != null) {
                isSameImage = oldImage.endsWith(newImage);
            }
            if (!isSameImage) {
                helper.deleteImage(oldImage);
                String fileName = helper.saveImage(image);
                product.setImage(fileName);
            }
        }
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if (product.getImage() != null) {
            helper.deleteImage(product.getImage());
        }
        productRepository.delete(product);
    }
}
