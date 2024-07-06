package com.cqrs.command;

import com.cqrs.command.entities.Product;
import com.cqrs.command.repository.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class Application {
//public class Application implements CommandLineRunner {

    private final ProductRepo productRepo;

    public Application(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        UUID uuid=UUID.fromString("a0ded117-1167-4cdf-988d-b4caed65ed8e");
////        Product product = Product.builder()
////                .pName("Mi_10")
////                .pDescription("Best Mobile under 20,000.00")
////                .uuid(UUID.randomUUID())
////                .build();
//        Optional<Product> byUuid = productRepo.findByUuid(uuid);
//        Product updated = byUuid.get();
//        updated.setPrice(18999.00);
//
//
//
//        Product save = productRepo.save(updated);
//
//        System.out.println(save);
//    }
}
