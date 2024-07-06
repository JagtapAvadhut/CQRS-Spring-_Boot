package com.cqrs.command.service;

import com.cqrs.command.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Object> createProduct(ProductDto productDto);
    ResponseEntity<Object> updateProduct(Long pId, ProductDto productDto);
    ResponseEntity<Object> deleteProduct(Long pId);
}
