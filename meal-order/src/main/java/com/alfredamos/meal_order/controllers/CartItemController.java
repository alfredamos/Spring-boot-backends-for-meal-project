package com.alfredamos.meal_order.controllers;

import com.alfredamos.meal_order.dto.CartItemDto;
import com.alfredamos.meal_order.services.CartItemService;
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
@Tag(name="cart-items")
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllCartItems(){
        var cartItemsDto = this.cartItemService.getAllCartItems();

        return new ResponseEntity<>(cartItemsDto, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable UUID id){
        var cartItemDto = this.cartItemService.getCartItemById(id);

        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemDto> createCartItem(@Valid @RequestBody CartItemDto cartItemDto){
        this.cartItemService.create(cartItemDto);

        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCartItem(@PathVariable UUID id){
        var result = this.cartItemService.deleteCartItem(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartItemDto> editCartItem(@Valid @PathVariable UUID id, CartItemDto cartItemDto){
        this.cartItemService.editCartItem(id, cartItemDto);

        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }
}
