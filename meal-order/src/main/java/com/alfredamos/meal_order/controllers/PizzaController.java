package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.services.PizzaService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Tag(name = "pizzas")
@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getAllPizzas() {
        var pizzasDto = this.pizzaService.getAllPizzas();

        return new ResponseEntity<>(pizzasDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable UUID id){
        var pizzaDto = this.pizzaService.getPizzaById(id);

        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }


}
