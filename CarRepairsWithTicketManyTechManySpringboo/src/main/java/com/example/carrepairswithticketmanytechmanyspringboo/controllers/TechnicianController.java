package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TechnicianEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.services.ITechnicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/technicians")
@RequiredArgsConstructor
public class TechnicianController {
    private final ITechnicianService technicianService;

    @PostMapping
    public ResponseEntity<?> createTechnician(@Valid @RequestBody TechnicianCreate request){
        var response = technicianService.createTechnician(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> deleteTechnicianById(@PathVariable UUID id){
        var response = technicianService.deleteTechnicianById(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByTechId(#id)")
    public ResponseEntity<?> editTechnicianById(@PathVariable UUID id, @RequestBody @Valid TechnicianEdit request){
        var response = technicianService.editTechnicianById(id, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByTechId(#id)")
    public ResponseEntity<?> getTechnicianById(@PathVariable UUID id){
        var response = technicianService.getTechnicianById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-user-id/{userId}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByUserId(#userId)")
    public ResponseEntity<?> getTechnicianByUserId(@PathVariable UUID userId){
        var response = technicianService.getTechnicianByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getAllTechnicians(){
        var response = technicianService.getAllTechnicians();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-specialty/{specialty}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getTechniciansBySpecialty(@PathVariable String specialty){
        var response = technicianService.getTechniciansBySpecialty(specialty);

        return ResponseEntity.ok(response);
    }
}
