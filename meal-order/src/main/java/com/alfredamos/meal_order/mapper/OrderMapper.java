package com.alfredamos.meal_order.mapper;

import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);

    @Mapping(source = "user.id", target = "userId")
    OrderDto toDTO(Order order);

    List<OrderDto> toDTOList(List<Order> orders);
}
