package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.ToTechnicianResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Technician;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.TechMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.TechnicianRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.UserRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TechnicianService implements ITechnicianService{
    private final TechnicianRepository technicianRepository;
    private final TechMapper techMapper;
    private final UserRepository userRepository;

    @Override
    public TechnicianResponse createTechnician(TechnicianCreate request) {
        //----> Get the user by id.
        var user = userRepository.getUsersById(request.getUserId());

        //----> Map the technician create request to the technician entity.
        var requestEntity = techMapper.toEntity(request);
        requestEntity.setUser(user);

        //----> Create the technician.
        var newTech = technicianRepository.save(requestEntity);

        //----> Send back response.
        return ToTechnicianResponse.toTechnicianResponse(newTech);
    }

    @Transactional
    @Override
    public ResponseMessage deleteTechnicianById(UUID id) {
        //----> Check for null technician.
        var tech = this.getOneTechnician(id);

        //----> Delete the technician with the associated user.
        var deletedTech = userRepository.deleteUserById(tech.getUser().getId());

        //----> Send back response.
        return ResponseMessage.builder().message("Technician deleted successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public TechnicianResponse editTechnicianById(UUID id, TechnicianEdit request) {
        //----> Get the user by id.
        var user = userRepository.getUsersById(request.getUserId());

        //----> Check for null technician.
        this.getOneTechnician(id);

        //----> Map TechnicianEdit to Technician.
        var techEntity = techMapper.toEntity(request);
        techEntity.setId(id);
        techEntity.setUser(user);

        //----> Save the edited technician.
        var editedTech = technicianRepository.save(techEntity);

        //----> Return the edited technician.
        return ToTechnicianResponse.toTechnicianResponse(editedTech);
    }

    @Override
    public List<TechnicianResponse> getAllTechnicians() {
        //----> Fetch all technicians.
        var technicians = technicianRepository.findAll();

        //----> Return the technicians.
        return technicians.stream().map(ToTechnicianResponse::toTechnicianResponse).toList();
    }

    @Override
    public List<TechnicianResponse> getTechniciansBySpecialty(String specialty) {
        //----> Fetch technicians by specialty.
        var technicians = technicianRepository.getTechniciansBySpecialty(specialty);

        //----> Return the technicians.
        return technicians.stream().map(ToTechnicianResponse::toTechnicianResponse).toList();
    }

    @Override
    public TechnicianResponse getTechnicianById(UUID id) {
       //----> Get the technician by id.
       var tech = this.getOneTechnician(id);

       //----> Return the technician.
       return ToTechnicianResponse.toTechnicianResponse(tech);
    }

    @Override
    public TechnicianResponse getTechnicianByUserId(UUID id) {
        //----> Get the technician by user id.
        var tech = technicianRepository.getTechniciansByUserId(id);

        //----> Return the technician.
        return ToTechnicianResponse.toTechnicianResponse(tech);
    }

    private Technician getOneTechnician(UUID id){
        return technicianRepository.findById(id).orElseThrow(() -> new NotFoundException("Technician not found!"));
    }
}
