package com.example.carrepairshopspringbackend.mapper;

import com.example.carrepairshopspringbackend.dtos.UserDto;
import com.example.carrepairshopspringbackend.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDTO(User user);

    List<UserDto> toDTOList(List<User> users);
}
