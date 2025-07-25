package com.alfredamos.meal_order.services;


import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.entities.CartItem;
import com.alfredamos.meal_order.exceptions.BadRequestException;
import com.alfredamos.meal_order.exceptions.NotFoundException;
import com.alfredamos.meal_order.mapper.CartItemMapper;
import com.alfredamos.meal_order.mapper.OrderMapper;
import com.alfredamos.meal_order.mapper.PizzaMapper;
import com.alfredamos.meal_order.mapper.UserMapper;
import com.alfredamos.meal_order.repositories.CartItemRepository;
import com.alfredamos.meal_order.repositories.OrderRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final PizzaService pizzaService;
    private final OrderMapper orderMapper;
    private final CartItemMapper cartItemMapper;
    private final UserMapper userMapper;
    private final PizzaMapper pizzaMapper;

    public OrderDto createOrder(OrderDto orderDto) {

        if (orderDto.getCartItemsDto() != null) {
            var order = this.orderMapper.toEntity(orderDto);

            //----> Get the cartItems.
            var cartItems = this.setCartItemList(orderDto);

            var userId = orderDto.getUserId();

            //----> Get the user creating the order.
            var userDto = this.userService.getUserById(userId);

            var user = this.userMapper.toEntity(userDto);

            //----> Attache the user to order.
            order.setUser(user);

            //----> Attach the cart-items to the order.
            order.setCartItems(cartItems);

            var adjustedOrder = order.setNewOrder(user);

            //----> Create new order.
            var newOrder = this.orderRepository.save(adjustedOrder);

            //----> Save the cart-items into the cart-items table.
            for (CartItem cartItem : cartItems){
                cartItem.setOrder(newOrder);
                this.cartItemRepository.save(cartItem);

            }

            //----> Attach the newly created order to the cart-items.
            newOrder.setCartItems(cartItems);

            //----> Map order to orderDto.
            var newOrderDto = this.orderMapper.toDTO(newOrder);
            newOrderDto.setCartItemsDto(orderDto.getCartItemsDto());

            return newOrderDto;


        }

        return new OrderDto();
    }


    public ResponseMessage deleteOrderById(UUID id){
        //----> Check for existence of order.
        this.checkForOrderExistence(id);

        //----> Delete the order with given id from the database.
        this.orderRepository.deleteById(id);

        return new ResponseMessage("Success", "Order has been deleted successfully!", 200);

    }

    public ResponseMessage deleteOrdersByUser(UUID userId){
        //----> Get the user.
        var userDto = this.userService.getUserById(userId);

        var user = this.userMapper.toEntity(userDto);

        //----> Delete all orders associated with this user.
        this.orderRepository.deleteOrdersByUser(user);

        return new ResponseMessage("Success", "All orders associated with this user are deleted!", 200);
    }

    public OrderDto editOrderById(UUID id){
        //----> Check for existence of order.
        this.checkForOrderExistence(id);

        var order = this.orderRepository.findById(id).orElse(null);

        return this.orderMapper.toDTO(order);

    }

    public OrderDto deliveredOrder(UUID orderId){
        //----> Check for existence of order.
        this.checkForOrderExistence(orderId);

        //----> Get the order.
        var order = this.orderRepository.findById(orderId).orElse(null);

        assert order != null;
        if (!order.getIsShipped()) {
            throw new BadRequestException("Order must be shipped before delivery, please ship the order!");
        }

        //----> Update the order delivery info.
        var deliveredOrder = order.deliveryInfo();

        //----> Update the order delivery info in the database.

        var editedOrder = this.orderRepository.save(deliveredOrder);

        return this.orderMapper.toDTO(editedOrder);
    }

    public List<OrderDto> getAllOrdersByUser(UUID userId){
        //----> Get the user associated with the orders.
        var userDto = this.userService.getUserById(userId);

        var user = this.userMapper.toEntity(userDto);

        var orders = this.orderRepository.findOrdersByUser(user);

        return this.orderMapper.toDTOList(orders);
    }

    public List<OrderDto> getAllOrders() {
        var orders = this.orderRepository.findAll();

        return this.orderMapper.toDTOList(orders);
    }

    public OrderDto getOrderById(UUID id){
        //----> Check for existence of order.
        this.checkForOrderExistence(id);

        var order = this.orderRepository.findById(id).orElse(null);

        return this.orderMapper.toDTO(order);
    }

    public OrderDto shippedOrder(UUID orderId){
        //----> Check for existence of order.
        this.checkForOrderExistence(orderId);

        //----> Get the order.
        var order = this.orderRepository.findById(orderId).orElse(null);

        //----> Update the shipping information.
        assert order != null;
        var shippedOrder = order.shippingInfo();

        //----> Update the order in the database.

        var editedOrder = this.orderRepository.save(shippedOrder);

        return this.orderMapper.toDTO(editedOrder);

    }

    private void checkForOrderExistence(UUID id){
        var exist = this.orderRepository.existsById(id);

        //----> Check for existence of order.
        if (!exist){
            throw new NotFoundException("Order does not exist!");
        }
    }

    private List<CartItem> setCartItemList(OrderDto orderDto){
       //----> Map the cart-items-dto to cart-items with the associated pizzas.
       return orderDto.getCartItemsDto().stream().map(cartItemDto -> {
           var cartItem = this.cartItemMapper.toEntity(cartItemDto);

           var pizzaDto = this.pizzaService.getPizzaById(cartItemDto.getPizzaId());

           cartItem.setPizza(this.pizzaMapper.toEntity(pizzaDto));

           return cartItem;

       }).toList();

    }
}
