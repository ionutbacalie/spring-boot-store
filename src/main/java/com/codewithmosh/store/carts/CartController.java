package com.codewithmosh.store.carts;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.codewithmosh.store.products.ProductNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart (
        UriComponentsBuilder uriBuilder
    ) {
        var cartDto = cartService.createCart();

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adds a product to the cart.")
    public ResponseEntity<CartItemDto> addtoCart(
        @Parameter(description = "The id of the cart.")
         @PathVariable UUID cartId,
        @RequestBody AddItemToCartRequest request
    ) {
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable(name = "cartId") UUID cartId) {
        return cartService.getCart(cartId);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCart(
        @PathVariable UUID cartId,
        @PathVariable Long productId,
        @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateCart(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItem (
        @PathVariable("cartId") UUID cartId,
        @PathVariable("productId") Long productId
    ) {
        cartService.removeItem(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> clearCart (
        @PathVariable("cartId") UUID cartId
    ) {
        cartService.clearCart(cartId);
        
        return ResponseEntity.notFound().build();
    }  
    
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cart was not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Product not found in the cart."));
    }
}
