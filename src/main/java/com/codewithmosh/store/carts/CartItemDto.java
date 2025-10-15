package com.codewithmosh.store.carts;

import java.math.BigDecimal;

import com.codewithmosh.store.products.CartProductDto;

import lombok.Data;

@Data
public class CartItemDto {
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}
