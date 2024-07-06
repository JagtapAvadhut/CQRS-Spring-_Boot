package com.cqrs.command.service.serviceImpl;

import com.cqrs.command.dto.ProductDto;
import com.cqrs.command.entities.Product;
import com.cqrs.command.enums.ProductEvent;
import com.cqrs.command.kafka.KafkaConfig;
import com.cqrs.command.repository.ProductRepo;
import com.cqrs.command.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private KafkaTemplate<String, Object> template;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public ResponseEntity<Object> createProduct(ProductDto productDto) {
        try {
            Product product = Product.builder()
                    .name(productDto.getName())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .uuid(UUID.randomUUID())
                    .build();
            Product savedProduct = productRepo.save(product);
            template.send(KafkaConfig.PRODUCT_SAVE, savedProduct);
            LOGGER.info("Saved Product: {}", ProductEvent.PRODUCT_CREATED);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error creating product: {}", e);
            return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(Long pId, ProductDto productDto) {
        try {
            Optional<Product> byId = productRepo.findById(pId);
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
            Product product = byId.get();
            updateProductFromDto(product, productDto);
            Product updatedProduct = productRepo.save(product);
            template.send(KafkaConfig.PRODUCT_UPDATE, updatedProduct);

            LOGGER.info("Updated Product: {}", ProductEvent.PRODUCT_UPDATED);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updating product: {}", e.getMessage());
            return new ResponseEntity<>("Error updating product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long pId) {
        try {
            Optional<Product> byId = productRepo.findById(pId);
            if (byId.isEmpty()) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
            productRepo.deleteById(pId);
            template.send(KafkaConfig.PRODUCT_DELETE, byId.get().getUuid());

            LOGGER.info("Deleted Product with id: {} : {}", pId, ProductEvent.PRODUCT_DELETED);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error deleting product: {}", e.getMessage());
            return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateProductFromDto(Product product, ProductDto productDto) {
        if (productDto.getName() != null) {
            product.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            product.setDescription(productDto.getDescription());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
    }
}
