package com.example.demo.exception;

public class IncorrectPeselLength extends RuntimeException{
    public IncorrectPeselLength() {
        super("PESEL must have 11 digits");
    }
}
