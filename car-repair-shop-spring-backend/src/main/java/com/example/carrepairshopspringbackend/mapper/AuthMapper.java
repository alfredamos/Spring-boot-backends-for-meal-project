package com.example.carrepairshopspringbackend.mapper;


import com.example.carrepairshopspringbackend.dtos.EditProfile;
import com.example.carrepairshopspringbackend.dtos.Signup;
import com.example.carrepairshopspringbackend.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toEntity(Signup signup);
    User toEntity(EditProfile editProfile);
}
