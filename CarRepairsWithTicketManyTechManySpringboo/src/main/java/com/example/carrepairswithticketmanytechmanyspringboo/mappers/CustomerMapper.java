package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
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
