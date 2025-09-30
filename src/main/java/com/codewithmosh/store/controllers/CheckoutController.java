package com.codewithmosh.store.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.CartService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@RestController("/checkout")
@Data
public class CheckoutController {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(
        @Valid @RequestBody CheckoutRequest request
    ) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null); 
        if(cart == null) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Cart not found.")
            );
        }

        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Cart is empty") 
            );
        }

        var order = new Order();
        order.setTotal_price(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());


        cart.getItems().forEach( (CartItem item) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotal_price(item.getTotalPrice());
            order.getItems().add(orderItem);
        });
        
        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}
