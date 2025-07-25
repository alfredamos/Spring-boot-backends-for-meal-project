package com.alfredamos.meal_order.services;

import com.alfredamos.meal_order.entities.CartItem;
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

    //----> Create new resource.
    public CartItem create(CartItem cartItem){
        return this.cartItemRepository.save(cartItem);
    }

    //----> Delete resource with given id.
    public ResponseMessage deleteCartItem(UUID id) {
        var exist = this.cartItemRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        this.cartItemRepository.deleteById(id);

        return new ResponseMessage("Success","CartItem has been deleted successfully!", 200);
    }

    //----> Edit resource with given id.
    public CartItem editCartItem(UUID id, CartItem cartItem) {
        var exist = this.cartItemRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        return this.cartItemRepository.save(cartItem);
    }

    //----> Find all resources.
    public List<CartItem> getAllCartItems(){
        return this.cartItemRepository.findAll();
    }

    //---->
    public Optional<CartItem> getCartItemById(UUID id) {
        var exist = this.cartItemRepository.existsById(id);

        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist!");
        }

        return this.cartItemRepository.findById(id);
    }
}
