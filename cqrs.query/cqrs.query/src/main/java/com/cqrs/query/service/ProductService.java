package com.cqrs.query.service;

import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<Object>listOfProduct();
}
