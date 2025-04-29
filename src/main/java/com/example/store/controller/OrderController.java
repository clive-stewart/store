package com.example.store.controller;

import com.example.store.dto.OrderDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;
import com.example.store.mapper.OrderMapper;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.OrderRepository;

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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Order addProductToOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        order.getProducts().add(product);
        return orderRepository.save(order);
    }

    @Async
    @GetMapping
    @Cacheable(value = "orderDTO")
    public List<OrderDTO> getAllOrders() {
        return (List<OrderDTO>) CompletableFuture.completedFuture(orderMapper.ordersToOrderDTOs(orderRepository.findAll()));
    }

    @Async
    @GetMapping
    @Cacheable(value = "orderDTO")
    public List<ProductDTO> getAllProductsInOrder() {
        return (List<ProductDTO>) CompletableFuture.completedFuture(productMapper.productToProductDTO((Product) productRepository.findAll()));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody Order order) {
        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }

    @Async
    @Cacheable(value = "order", key = "#id")
    @GetMapping("/{id}")
    public CompletableFuture<Order> getOrderById(@PathVariable Long id) {
        Optional<CompletableFuture<Order>> order = Optional.ofNullable(CompletableFuture.completedFuture(orderMapper.findById(id)));
        return order.orElseThrow();
    }

}
