package com.codewithmosh.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NotNull
public class AddItemToCartRequest {
    private Long productId;
}
