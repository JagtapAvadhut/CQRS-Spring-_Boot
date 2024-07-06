package com.cqrs.query.repository;

import com.cqrs.query.entities.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Products, String> {
    Optional<Products> findByUuid(String uuid);
}
