package com.nca.api_clientes.controllers;

import com.nca.api_clientes.dtos.ClienteRequest;
import com.nca.api_clientes.repositories.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {
        try {
            this.clienteRepository.salvar(clienteRequest);
            return ResponseEntity.status(201).body("Cliente cadastrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
