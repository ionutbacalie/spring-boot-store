package com.codewithmosh.store.carts;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException() {
        super("Cart is empty.");
    }
}
