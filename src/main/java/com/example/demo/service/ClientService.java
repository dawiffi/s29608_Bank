package com.example.demo.service;

import com.example.demo.exception.BalanceLessThanZero;
import com.example.demo.exception.IncorrectCurrency;
import com.example.demo.exception.IncorrectPeselLength;
import com.example.demo.model.Client;
import com.example.demo.model.Currency;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import static com.example.demo.model.Currency.*;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private static Integer idClient = 1;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String name, String surname, String pesel, Currency currency, Double balance) {
        if (balance < 0){
            throw new BalanceLessThanZero();
        }
        if (pesel.length() != 11){
            throw new IncorrectPeselLength();
        }
        if (currency != PLN && currency != EUR && currency != USD){
            throw new IncorrectCurrency();
        }
        Client newClient = new Client(idClient, name, surname, pesel, currency, balance);
        idClient++;
        this.clientRepository.addClient(newClient);
        return newClient;
    }

    public Client getClientById(Integer id){
        return this.clientRepository
                .getClientList().stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Client getClientWithBalanceGreaterThan(Double balance) {
        if (balance > 1000) {
            return this.clientRepository
                    .getClientList().stream()
                    .filter(client -> client.getBalance() > balance)
                    .findFirst()
                    .orElse(null);
        } else if (balance < 0) {
            throw new BalanceLessThanZero();
        } else {
            return this.clientRepository
                    .getClientList().stream()
                    .filter(client -> client.getBalance() > balance)
                    .findFirst()
                    .orElse(null);
        }
    }

    public void addBalance(Integer id, Double amountToAdd) {
        Client client = getClientById(id);
        client.setBalance(client.getBalance() + amountToAdd);
    }

    public void subtractBalance(Integer id, Double amountToSubtract) {
        Client client = getClientById(id);
        if(amountToSubtract > client.getBalance()){
            throw new BalanceLessThanZero();
        }
        client.setBalance(client.getBalance() - amountToSubtract);
    }
}
