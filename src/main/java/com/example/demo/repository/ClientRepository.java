package com.example.demo.repository;

import com.example.demo.model.Client;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientRepository {
    @Getter
    private static List<Client> clientList = new ArrayList<>();

    public static void setClientList(List<Client> clientList) {
        ClientRepository clientRepository = new ClientRepository();
        ClientRepository.clientList = clientList;
    }

    public void addClient(Client client) {
        clientList.add(client);
    }
}
