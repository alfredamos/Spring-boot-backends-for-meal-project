package com.alfredamos.meal_order.mapper;

import com.alfredamos.meal_order.dto.UserDto;
import com.alfredamos.meal_order.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDTO(User user);

    List<UserDto> toDTOList(List<User> users);
}
