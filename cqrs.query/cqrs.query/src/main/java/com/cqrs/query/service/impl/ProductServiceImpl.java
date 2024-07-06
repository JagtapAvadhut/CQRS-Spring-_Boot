package com.cqrs.query.service.impl;

import com.cqrs.query.entities.Products;
import com.cqrs.query.repository.ProductRepo;
import com.cqrs.query.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepo productRepo;

    @Override
    public ResponseEntity<Object> listOfProduct() {
        try {

            List<Products> products = productRepo.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception exception) {
            LOGGER.error("Error listOfProducts :{}", exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
