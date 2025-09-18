package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class AddItemToCartRequest {
    private Long productId;
}
