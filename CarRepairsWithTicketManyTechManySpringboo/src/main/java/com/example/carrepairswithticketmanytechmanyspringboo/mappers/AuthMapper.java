package com.example.carrepairswithticketmanytechmanyspringboo.mappers;


import com.example.carrepairswithticketmanytechmanyspringboo.dto.EditUserProfile;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.SignupUser;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toEntity(SignupUser signup);
    User toEntity(EditUserProfile editProfile);
}
