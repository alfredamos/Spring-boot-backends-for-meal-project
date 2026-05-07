package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;

import java.util.List;
import java.util.UUID;

public interface ITechnicianService {
    TechnicianResponse createTechnician(TechnicianCreate request);
    ResponseMessage deleteTechnicianById(UUID id);
    TechnicianResponse editTechnicianById(UUID id, TechnicianEdit request);
    List<TechnicianResponse> getAllTechnicians();
    List<TechnicianResponse> getTechniciansBySpecialty(String specialty);
    TechnicianResponse getTechnicianById(UUID id);
    TechnicianResponse getTechnicianByUserId(UUID id);
}
