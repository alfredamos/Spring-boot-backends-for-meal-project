package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.dto.CartItemDto;
import com.alfredamos.meal_order.entities.CartItem;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.mapper.CartItemMapper;
import com.alfredamos.meal_order.repositories.CartItemRepository;
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
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    //----> Create new resource.
    public void create(CartItemDto cartItemDto){
        var cartItem = this.cartItemMapper.toEntity(cartItemDto);
        this.cartItemRepository.save(cartItem);
    }

    //----> Delete resource with given id.
    public ResponseMessage deleteCartItem(UUID id) {
        //----> Check for existence of cart-item.
        checkForOrderExistence(id);

        //----> Delete the cart-item from the database.
        this.cartItemRepository.deleteById(id);

        return new ResponseMessage("Success","CartItem has been deleted successfully!", 200);
    }

    //----> Edit resource with given id.
    public void editCartItem(UUID id, CartItemDto cartItemDto) {
        //----> Check for existence of cart-item.
        checkForOrderExistence(id);

        var cartItem = this.cartItemMapper.toEntity(cartItemDto);

        //----> Save edited-cart-item into the database.
        this.cartItemRepository.save(cartItem);
    }

    //----> Find all resources.
    public List<CartItemDto> getAllCartItems(){
        var cartItems = this.cartItemRepository.findAll();

        return this.cartItemMapper.toDTOList(cartItems);
    }

    //---->
    public CartItemDto getCartItemById(UUID id) {
        //----> Check for existence of cart-item.
        checkForOrderExistence(id);

        var cartItem =  this.cartItemRepository.findById(id).orElse(null);

        return this.cartItemMapper.toDTO(cartItem);
    }

    private void checkForOrderExistence(UUID id){
        var exist = this.cartItemRepository.existsById(id);

        //----> Check for existence of order.
        if (!exist){
            throw new NotFoundException("Order does not exist!");
        }
    }
}
