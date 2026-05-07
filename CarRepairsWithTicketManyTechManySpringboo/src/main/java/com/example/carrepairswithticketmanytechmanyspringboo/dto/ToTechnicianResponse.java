package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Technician;

public class ToTechnicianResponse {
    public static TechnicianResponse toTechnicianResponse(Technician tech){
        return TechnicianResponse.builder().
                id(tech.getId()).
                specialty(tech.getSpecialty()).
                userId(tech.getUser().getId()).
                name(tech.getUser().getName()).
                email(tech.getUser().getEmail()).
                phone(tech.getUser().getPhone()).
                image(tech.getUser().getImage()).
                dateOfBirth(tech.getUser().getDateOfBirth()).
                gender(tech.getUser().getGender()).
                build();
    }
}
