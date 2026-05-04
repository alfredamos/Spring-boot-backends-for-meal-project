package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.UserDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDTO(User user);

    List<UserDto> toDTOList(List<User> users);
}
