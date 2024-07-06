package com.cqrs.query.controller;

import com.cqrs.query.entities.Products;
import com.cqrs.query.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ProductRepo productRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "PRODUCT-SAVE", groupId = "group-1")
    public void listenSave(String message) {
        try {
            LOGGER.info("Received PRODUCT-SAVE message: {}", message);
            Products product = objectMapper.readValue(message, Products.class);
            productRepo.save(product);
            LOGGER.info("Product saved: {}", product);
        } catch (IOException e) {
            LOGGER.error("Error processing PRODUCT-SAVE message: {}", message, e);
        }
    }

    @KafkaListener(topics = "PRODUCT-UPDATE", groupId = "group-1")
    public void listenUpdate(String message) {
        try {
            LOGGER.info("Received PRODUCT-UPDATE message: {}", message);
//            String uuid = message.replace("\"", "");
            Products updatedProduct = objectMapper.readValue(message, Products.class);
            Optional<Products> existingProductOpt = productRepo.findByUuid(updatedProduct.getUuid());

            if (existingProductOpt.isPresent()) {
                Products existingProduct = existingProductOpt.get();
                // Update the existing product with the new data
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setDescription(updatedProduct.getDescription());
                existingProduct.setPrice(updatedProduct.getPrice());
                // Save the updated product
                productRepo.save(existingProduct);
                LOGGER.info("Product updated: {}", existingProduct);
            } else {
                LOGGER.warn("Product with id {} not found for update", updatedProduct.getId());
            }
        } catch (IOException e) {
            LOGGER.error("Error processing PRODUCT-UPDATE message: {}", message, e);
        }
    }

    @KafkaListener(topics = "PRODUCT-DELETE", groupId = "group-1")
    public void listenDelete(String message) {
        try {
            LOGGER.info("Received PRODUCT-DELETE message: {}", message);
            String uuid = message.replace("\"", "");
            Optional<Products> existingProductOpt = productRepo.findByUuid(uuid);
            if (existingProductOpt.isPresent()) {
                productRepo.deleteById(existingProductOpt.get().getId());
                LOGGER.info("Product with UUID {} deleted", message);
            } else {
                LOGGER.warn("Product with UUID {} not found for deletion", message);
            }
        } catch (Exception e) {
            LOGGER.error("Error processing PRODUCT-DELETE message: {}", message, e);
        }
    }
}
