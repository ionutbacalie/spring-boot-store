package com.codewithmosh.store.orders;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {
    OrderProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
