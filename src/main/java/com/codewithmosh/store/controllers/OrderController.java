package com.codewithmosh.store.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.services.OrderService;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@RestController
@Data
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(
        @PathVariable("orderId") Long orderId
    ) {
        return orderService.getOrder(orderId);
    }

   @ExceptionHandler(OrderNotFoundException.class)
   public ResponseEntity<Void> handleOrderNotFoundException(OrderNotFoundException ex) {
       return ResponseEntity.notFound().build();
   }

   @ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getMessage()));
   }

}
