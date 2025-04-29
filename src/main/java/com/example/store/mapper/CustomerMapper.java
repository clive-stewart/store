package com.example.store.mapper;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Optional<Customer> customer);

    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customer);
}
