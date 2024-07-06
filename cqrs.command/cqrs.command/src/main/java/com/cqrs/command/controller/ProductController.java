package com.cqrs.command.controller;

import com.cqrs.command.dto.ProductDto;
import com.cqrs.command.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateProduct(@RequestParam Long pId, @RequestBody ProductDto productDto) {
        return productService.updateProduct(pId, productDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteProduct(@RequestParam Long pId) {
        return productService.deleteProduct(pId);
    }
}
