package com.alfredamos.meal_order.controllers;

import com.alfredamos.meal_order.dto.CartItemDto;
import com.alfredamos.meal_order.mapper.CartItemMapper;
import com.alfredamos.meal_order.services.CartItemService;
import com.alfredamos.meal_order.utils.ResponseMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllCartItems(){
        var cartItems = this.cartItemService.getAllCartItems();

        var cartItemsDto = this.cartItemMapper.toDTOList(cartItems);

        return new ResponseEntity<>(cartItemsDto, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable UUID id){
        var cartItem = this.cartItemService.getCartItemById(id).orElse(null);

        var cartItemDto = this.cartItemMapper.toDTO(cartItem);

        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartItemDto> createCartItem(@RequestBody CartItemDto cartItemDto){
        var cartItem = this.cartItemMapper.toEntity(cartItemDto);

        this.cartItemService.create(cartItem);

        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteCartItem(@PathVariable UUID id){
        var result = this.cartItemService.deleteCartItem(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartItemDto> editCartItem(@PathVariable UUID id, CartItemDto cartItemDto){
        var cartItem = this.cartItemMapper.toEntity(cartItemDto);

        this.cartItemService.editCartItem(id, cartItem);

        return new ResponseEntity<>(cartItemDto, HttpStatus.OK);
    }
}
