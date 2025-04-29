package com.example.store.mapper;

import com.example.store.dto.OrderDTO;
import com.example.store.dto.ProductOrderDTO;

import com.example.store.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<OrderDTO> productsToProductDTOs(List<Product> product);

    ProductOrderDTO productToProductDTO(Product product);

    Product findById(Long id);
}
