package com.example.demo.exception;

public class IncorrectCurrency extends RuntimeException{
    public IncorrectCurrency() {
        super("Incorrect currency");
    }
}
