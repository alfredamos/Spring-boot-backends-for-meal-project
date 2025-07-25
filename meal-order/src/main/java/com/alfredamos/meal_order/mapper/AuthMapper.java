package com.alfredamos.meal_order.mapper;


import com.alfredamos.meal_order.dto.EditProfile;
import com.alfredamos.meal_order.dto.Signup;
import com.alfredamos.meal_order.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toEntity(Signup signup);
    User toEntity(EditProfile editProfile);
}
