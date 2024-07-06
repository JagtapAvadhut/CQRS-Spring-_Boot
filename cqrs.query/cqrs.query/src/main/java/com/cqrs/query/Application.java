package com.cqrs.query;

import com.cqrs.query.entities.Products;
import com.cqrs.query.repository.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class Application {
//public class Application implements CommandLineRunner {
//
//    private final ProductRepo productRepo;
////
//    public Application(ProductRepo productRepo) {
//        this.productRepo = productRepo;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Products products = Products.builder()
//                .uuid(UUID.randomUUID().toString())
//                .pName("Mi_10")
//                .pDescription("Best Mobile Under 20000")
//                .build();
//
//        Products save = productRepo.save(products);
//        System.out.println(save.toString());
//    }
}
