package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianDto;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechMapper {
    Technician toEntity(TechnicianDto techDto);
    Technician toEntity(TechnicianCreate request);

    @Mapping(source = "user.id", target = "userId")
    TechnicianDto toDTO(Technician technician);

    List<TechnicianDto> toDTOList(List<Technician> techs);
}
