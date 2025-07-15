package com.alfredamos.meal_order.mapper;


import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.entities.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PizzaMapper {

    Pizza toEntity(PizzaDto pizzaDto);

    @Mapping(source = "user.id", target = "userId")
    PizzaDto toDTO(Pizza pizza);

    List<PizzaDto> toDTOList(List<Pizza> pizzas);

}
