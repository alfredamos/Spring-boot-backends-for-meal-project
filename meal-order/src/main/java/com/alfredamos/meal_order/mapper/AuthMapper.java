package com.alfredamos.meal_order.mapper;


import com.alfredamos.meal_order.entities.EditProfile;
import com.alfredamos.meal_order.entities.Signup;
import com.alfredamos.meal_order.entities.User;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toEntity(Signup signup);
    User toEntity(EditProfile editProfile);
}
