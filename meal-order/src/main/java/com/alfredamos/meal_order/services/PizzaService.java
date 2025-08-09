package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.dto.PizzaDto;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.mapper.PizzaMapper;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.repositories.PizzaRepository;
import com.alfredamos.meal_order.repositories.UserRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    //----> Create new resource.
    public void createPizza(PizzaDto pizzaDto){
        var user = this.userRepository.findById((pizzaDto.getUserId())).orElse(null);

        var pizza = pizzaMapper.toEntity(pizzaDto);
        pizza.setUser(user);

        this.pizzaRepository.save(pizza);
    }

    //----> Delete resource with given id.
    public ResponseMessage deletePizza(UUID id) {
        checkForOrderExistence(id); //----> Check for existence of pizza with the given id.

        this.pizzaRepository.deleteById(id);

        return new ResponseMessage("Success", "Pizza deleted successfully!", 200);
    }

    //----> Edit resource with given id.
    public void editPizza(UUID id, PizzaDto pizzaDto)  {
        checkForOrderExistence(id); //----> Check for existence of pizza with the given id.

        //System.out.println("Pizza-dto, pizzaDto : " + pizzaDto);

        //----> Get the user.
        var user = this.userRepository.findById((pizzaDto.getUserId())).orElse(null);

        var pizza = pizzaMapper.toEntity(pizzaDto);
        pizza.setId(id);
        pizza.setUser(user);


        this.pizzaRepository.save(pizza);
    }

    //----> Find all resources.
    public List<PizzaDto> getAllPizzas(){
        var pizzas = this.pizzaRepository.findAll();

        return this.pizzaMapper.toDTOList(pizzas);
    }

    //---->
    public PizzaDto getPizzaById(UUID id)  {
        checkForOrderExistence(id); //----> Check for existence of pizza with the given id.

        var pizza = this.pizzaRepository.findById(id).orElse(null);

        return this.pizzaMapper.toDTO(pizza);
    }

    private void checkForOrderExistence(UUID id){
        var exist = this.pizzaRepository.existsById(id);

        //----> Check for existence of order.
        if (!exist){
            throw new NotFoundException("Order does not exist!");
        }
    }
}
