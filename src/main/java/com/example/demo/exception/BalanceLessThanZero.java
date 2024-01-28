package com.example.demo.exception;

public class BalanceLessThanZero extends RuntimeException{
    public BalanceLessThanZero() {
        super("Balance cannot be less than zero");
    }
}
