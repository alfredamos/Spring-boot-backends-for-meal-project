package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.entities.Pizza;
import com.alfredamos.meal_order.repositories.PizzaRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Data
@AllArgsConstructor
@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    //----> Create new resource.
    public Pizza createPizza(Pizza pizza){
        System.out.println("In pizza-service, pizzaDto : " + pizza);
        return this.pizzaRepository.save(pizza);
    }

    //----> Delete resource with given id.
    public ResponseMessage deletePizza(UUID id) {
        var exist = this.pizzaRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        this.pizzaRepository.deleteById(id);

        return new ResponseMessage("Success", "Pizza deleted successfully!", 200);
    }

    //----> Edit resource with given id.
    public Pizza editPizza(UUID id, Pizza pizza)  {
        var exist = this.pizzaRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        return this.pizzaRepository.save(pizza);
    }

    //----> Find all resources.
    public List<Pizza> getAllPizzas(){
        return this.pizzaRepository.findAll();
    }

    //---->
    public Optional<Pizza> getPizzaById(UUID id)  {
        var exist = this.pizzaRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        return this.pizzaRepository.findById(id);
    }
}
