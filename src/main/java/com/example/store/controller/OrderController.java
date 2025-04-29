package com.example.store.controller;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

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

    @Async
    @GetMapping
    @Cacheable(value = "orderDTO")
    public List<OrderDTO> getAllOrders() {
        return (List<OrderDTO>) CompletableFuture.completedFuture(orderMapper.ordersToOrderDTOs(orderRepository.findAll()));
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
