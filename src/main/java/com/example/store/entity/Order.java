package com.example.store.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Collection;

@Entity
@Data
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public Collection<Product> getProducts() {
        return java.util.List.of();
    }
}
