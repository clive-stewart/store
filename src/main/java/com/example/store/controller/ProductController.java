package com.example.store.controller;

import com.example.store.dto.ProductOrderDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;

import com.example.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Async
    @GetMapping
    @Cacheable(value = "productDTO")
    public List<Product> getAllProducts() {
        List<Product> all = productRepository.findAll();
        for (Product product : (productRepository.findAll())) {
            all.add(product);
        }

        return all;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOrderDTO createProduct(@RequestBody Product product) {
        return productMapper.productToProductDTO(productRepository.save(product));
    }

    @Async
    @Cacheable(value = "order", key = "#id")
    @GetMapping("/{id}")
    public CompletableFuture<Product> getProductById(@PathVariable Long id) {
        Optional<CompletableFuture<Product>> order = Optional.ofNullable(CompletableFuture.<Product>completedFuture(productMapper.findById(id)));
        return order.orElseThrow();
    }

}
