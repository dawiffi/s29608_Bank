package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private Integer id;
    private String name;
    private String surname;
    private String pesel;
    private Currency currency;
    private Double balance;
}
