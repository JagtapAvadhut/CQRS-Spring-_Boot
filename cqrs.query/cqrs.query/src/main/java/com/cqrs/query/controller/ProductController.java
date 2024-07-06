package com.cqrs.query.controller;

import com.cqrs.query.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAll() {
        return productService.listOfProduct();
    }

}
