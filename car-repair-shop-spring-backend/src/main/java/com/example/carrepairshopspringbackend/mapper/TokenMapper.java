package com.example.carrepairshopspringbackend.mapper;

import com.example.carrepairshopspringbackend.dtos.TokenCreate;
import com.example.carrepairshopspringbackend.dtos.TokenDto;
import com.example.carrepairshopspringbackend.entities.Token;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    Token toEntity(TokenDto tokenDto);
    Token toEntity(TokenCreate request);

    @Mapping(source = "user.id", target = "userId")
    TokenDto toDTO(Token token);

    List<TokenDto> toDTOList(List<Token> tokens);
}
