package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TokenCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TokenDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Token;
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
