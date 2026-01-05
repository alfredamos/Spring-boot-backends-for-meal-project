package com.example.carrepairshopspringbackend.mapper;

import com.example.carrepairshopspringbackend.dtos.CustomerCreate;
import com.example.carrepairshopspringbackend.dtos.CustomerDto;
import com.example.carrepairshopspringbackend.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerDto customerDto);
    Customer toEntity(CustomerCreate request);

    @Mapping(source = "user.id", target = "userId")
    CustomerDto toDTO(Customer customer);

    List<CustomerDto> toDTOList(List<Customer> customers);
}
