package com.codewithmosh.store.exceptions;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException() {
        super("Cart is empty.");
    }
}
