package com.example.demo.service;

import com.example.demo.exception.BalanceLessThanZero;
import com.example.demo.exception.IncorrectPeselLength;
import com.example.demo.model.Client;
import com.example.demo.model.Currency;
import com.example.demo.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.model.Currency.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ClientServiceTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);
    }

    @Test
    void RegisterNewValidAccount() {
        Client client = clientService.registerClient("Jan", "Kowalski", "12345678901", PLN, 1500.0);
        assertEquals(1, client.getId());
        assertEquals("Jan", client.getName());
        assertEquals("Kowalski", client.getSurname());
        assertEquals("12345678901", client.getPesel());
        assertEquals(PLN, client.getCurrency());
        assertEquals(1500.0, client.getBalance());
    }

    @Test
    void RegisterNewAccountWithInvalidPeselLength() {
        assertThrows(IncorrectPeselLength.class, () -> {
            Client client = clientService.registerClient("Joanna", "Kowalska", "1234567890", PLN, 1500.0);
        });
    }

    @Test
    void RegisterNewAccountWithInvalidBalance(){
        assertThrows(BalanceLessThanZero.class, () -> {
            Client client = clientService.registerClient("Janusz", "Walczuk", "12345678901", PLN, -500.0);
        });
    }

    @Test
    void getClientById(){
Client client = clientService.registerClient("Jan", "Kowalski", "12345678901", PLN, 1500.0);
    assertEquals(1, client.getId());
    }


    @Test
    void testGetClientWithBalanceGreaterThan() {
        Double balance = 1000.0;

        List<Client> mockClients = List.of(
                new Client(1, "John", "Doe", "12345678901", PLN, 2000.0)
        );
        ClientRepository.setClientList(mockClients);

        System.out.println("Przed przeszukiwaniem: " + ClientRepository.getClientList());
        Client result = clientService.getClientWithBalanceGreaterThan(balance);
        System.out.println("Po przeszukiwaniu: " + ClientRepository.getClientList());


        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getSurname());
        assertEquals("12345678901", result.getPesel());
        assertEquals(Currency.PLN, result.getCurrency());
        assertEquals(2000.0, result.getBalance());
    }

    @Test
    void testGetClientWithBalanceGreaterThanThrowException() {
        Double negativeBalance = -100.0;

        assertThrows(BalanceLessThanZero.class, () ->
                clientService.getClientWithBalanceGreaterThan(negativeBalance));
    }

    @Test
    void testGetClientWithBalanceGreaterThanBelowThreshold() {
        Double belowThresholdBalance = 300.0;

        Client result = clientService.getClientWithBalanceGreaterThan(belowThresholdBalance);

        assertNull(result);
    }

    @Test
    void addBalanceTest() {
        Client client = clientService.registerClient("Jan", "Kowalski", "12345678901", PLN, 1500.0);
        clientService.addBalance(1, 500.0);
        assertEquals(2000.0, client.getBalance());
    }

    @Test
    void substrackBalanceTest() {
        Client client = clientService.registerClient("Jan", "Kowalski", "12345678901", PLN, 1500.0);
        clientService.subtractBalance(1, 500.0);
        assertEquals(1000.0, client.getBalance());
    }

}
