package com.alfredamos.meal_order.mapper;

import com.alfredamos.meal_order.dto.CartItemDto;
import com.alfredamos.meal_order.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toEntity(CartItemDto cartItemDto);

    @Mapping(source = "pizza.id", target = "pizzaId")
    CartItemDto toDTO(CartItem cartItem);

    List<CartItemDto> toDTOList(List<CartItem> cartItems);

    List<CartItem> toEntityList(List<CartItemDto> cartItemDtos);
}
