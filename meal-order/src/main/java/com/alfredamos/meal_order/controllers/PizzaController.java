package com.alfredamos.meal_order.controllers;


import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.mapper.PizzaMapper;
import com.alfredamos.meal_order.services.PizzaService;
import com.alfredamos.meal_order.services.UserService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final PizzaMapper pizzaMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getAllPizza() {
        var pizzas = this.pizzaService.getAllPizzas();

        var pizzasDto = this.pizzaMapper.toDTOList(pizzas);

        return new ResponseEntity<>(pizzasDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable UUID id){
        var pizza = this.pizzaService.getPizzaById(id).orElse(null);

        var pizzaDto = this.pizzaMapper.toDTO(pizza);

        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PizzaDto> createPizza(@RequestBody PizzaDto pizzaDto){
        var pizza = this.pizzaMapper.toEntity(pizzaDto);

        var user = this.userService.getUserById(pizzaDto.getUserId()).orElse(null);

        pizza.setUser(user);

        this.pizzaService.createPizza(pizza);

        return  new ResponseEntity<>(pizzaDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deletePizza(@PathVariable UUID id){
        var result = this.pizzaService.deletePizza(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PizzaDto> editPizza(@PathVariable UUID id, @RequestBody PizzaDto pizzaDto){
        var pizza = this.pizzaMapper.toEntity(pizzaDto);

        var user = this.userService.getUserById(pizzaDto.getUserId()).orElse(null);

        pizza.setUser(user);

        this.pizzaService.editPizza(id, pizza);

        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }

}
