package com.codewithmosh.store.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
public class OrderController {
    @PostMapping("/checkout")
    public String checkout(
        @PathParam 
    ) {

    }
}
