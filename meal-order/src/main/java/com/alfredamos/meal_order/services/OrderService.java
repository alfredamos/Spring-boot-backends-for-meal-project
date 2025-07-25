package com.alfredamos.meal_order.services;


import com.alfredamos.meal_order.dto.CartItemDto;
import com.alfredamos.meal_order.dto.OrderDto;
import com.alfredamos.meal_order.entities.CartItem;
import com.alfredamos.meal_order.entities.Order;
import com.alfredamos.meal_order.entities.Status;
import com.alfredamos.meal_order.entities.User;
import com.alfredamos.meal_order.repositories.CartItemRepository;
import com.alfredamos.meal_order.repositories.OrderRepository;
import com.alfredamos.meal_order.utils.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final PizzaService pizzaService;

    public Order createOrder(OrderDto orderDto) {


        if (orderDto.getCartItemsDto() != null) {

            //----> Get the cartItems.
            var cartItems = this.setNewCartItems(orderDto);

            var adjustedOrder = this.setNewOrders(cartItems, orderDto);

            //----> Create new order.
            var newOrder = this.orderRepository.save(adjustedOrder);

            for (CartItem cartItem : cartItems){
                cartItem.setOrder(newOrder);
                this.cartItemRepository.save(cartItem);

            }
            newOrder.setCartItems(cartItems);

            return newOrder;


        }

        return new Order();
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
        var user = this.userService.getUserById(userId).orElse(null);

        //----> Delete all orders associated with this user.
        this.orderRepository.deleteOrdersByUser(user);

        return new ResponseMessage("Success", "All orders associated with this user are deleted!", 200);
    }

    public Order editOrderById(UUID id){
        //----> Check for existence of order.
        this.checkForOrderExistence(id);

        return this.orderRepository.findById(id).orElse(null);
    }

    public Order deliveredOrder(UUID orderId){
        //----> Check for existence of order.
        this.checkForOrderExistence(orderId);

        //----> Get the order.
        var order = this.orderRepository.findById(orderId).orElse(null);

        if (!order.getIsShipped()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be shipped before delivery, please ship the order!");
        }

        //----> Update the order delivery info.
        var deliveredOrder = this.deliveryInfo(order);

        //----> Update the order delivery info in the database.
        var updatedOrder = this.orderRepository.save(deliveredOrder);

        return updatedOrder;
    }

    public List<Order> getAllOrdersByUser(User user){
        return this.orderRepository.findOrdersByUser(user);
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> getOrderById(UUID id){
        //----> Check for existence of order.
        this.checkForOrderExistence(id);

        return this.orderRepository.findById(id);
    }

    public Order shippedOrder(UUID orderId){
        //----> Check for existence of order.
        this.checkForOrderExistence(orderId);

        //----> Get the order.
        var order = this.orderRepository.findById(orderId).orElse(null);

        //----> Update the shipping information.
        var shippedOrder = this.shippingInfo(order);

        //----> Update the order in the database.
        var updatedOrder = this.orderRepository.save(shippedOrder);

        return updatedOrder;

    }

    private Order deliveryInfo(Order order){
        //----> Update the order delivery info.
        order.setIsDelivered(true); //----> Order shipped.
        order.setDeliveryDate(LocalDate.now()); //----> Order shipping date.
        order.setStatus(Status.Delivered); //----> Order status.
        //----> Return the updated order
        return order;
    }

    private Order shippingInfo(Order order){
        //----> Update the order shipping info.
        order.setIsShipped(true); //----> Order shipped.
        order.setIsPending(false); //----> Order no longer pending.
        order.setShippingDate(LocalDate.now()); //----> Order shipping date.
        order.setStatus(Status.Shipped); //----> Order status.

        //----> Return the updated order.
        return order;
    }

    private void checkForOrderExistence(UUID id){
        var exist = this.orderRepository.existsById(id);

        //----> Check for existence of order.
        if (!exist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order does not exist!");
        }
    }

    protected Order setNewOrders(List<CartItem> cartItems, OrderDto orderDto){
        var userId = orderDto.getUserId();

        var user = this.userService.getUserById(userId).orElse(null);

        Order order = new Order();
        order.setUser(user);

        var totalQuantity = cartItems.stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
        var totalPrice = cartItems.stream().map(cart -> cart.getPrice() * cart.getQuantity()).reduce(0.0, Double::sum);


        var paymentId = order.getPaymentId() == null ?   UUID.randomUUID().toString() : order.getPaymentId();


        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setIsPending(true);
        order.setPaymentId(paymentId);
        order.setStatus(Status.Pending);

        order.setIsDelivered(false);
        order.setIsShipped(false);
        order.setCartItems(cartItems);


        return order;
    }

    protected List<CartItem> setNewCartItems(OrderDto orderDto) {
        List<CartItem> cartItems = new ArrayList<>();

        for (CartItemDto cartItemDto : orderDto.getCartItemsDto()) {
            CartItem cartItem = new CartItem();
            var pizza = this.pizzaService.getPizzaById(cartItemDto.getPizzaId()).orElse(null);


            cartItem.setName(cartItemDto.getName());
            cartItem.setImage(cartItemDto.getImage());
            cartItem.setPrice(cartItemDto.getPrice());
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setPizza(pizza);

            cartItems.add(cartItem);

        }

        return cartItems;
    }
}
