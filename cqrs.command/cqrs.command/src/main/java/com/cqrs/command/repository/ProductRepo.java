package com.cqrs.command.repository;

import com.cqrs.command.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
//    Optional<Product> findByPUuid(UUID pUuid);

}
