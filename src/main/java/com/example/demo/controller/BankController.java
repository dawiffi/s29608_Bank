package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Currency;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class BankController {
    private final ClientService clientService;

    @Autowired
    public BankController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/registerClient")
    public ResponseEntity<Client> registerClient(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String pesel,
            @RequestParam Double balance,
            @RequestParam Currency currency) {
        Client client = clientService.registerClient(name, surname, pesel, currency, balance);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/getClientById")
    public ResponseEntity<Client> getClientById(@RequestParam Integer id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/getClientWithBalanceGreaterThan")
    public ResponseEntity<Client> getClientWithBalanceGreaterThan(@RequestParam Double balance) {
        Client client = clientService.getClientWithBalanceGreaterThan(balance);
        return ResponseEntity.ok(client);
    }

}