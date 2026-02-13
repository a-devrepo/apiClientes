package com.nca.api_clientes.controllers;

import com.nca.api_clientes.dtos.ClienteRequest;
import com.nca.api_clientes.entities.Cliente;
import com.nca.api_clientes.repositories.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/{nome}")
    public ResponseEntity<?> obterPorNome(@PathVariable String nome) {
        try {
            var clientes = this.clienteRepository.obterPorNome(nome);
            return ResponseEntity.status(200).body(clientes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable UUID id, @RequestBody ClienteRequest cliente) {
        try {
            var clienteRegistrado = new Cliente();

            clienteRegistrado.setId(id);
            clienteRegistrado.setNome(cliente.nome());
            clienteRegistrado.setEmail(cliente.email());
            clienteRegistrado.setTelefone(cliente.telefone());

            if (!this.clienteRepository.atualizar(clienteRegistrado)) {

                return ResponseEntity.status(400).body("Cliente não encontrado.");
            }

            return ResponseEntity.status(200).body("Cliente atualizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try {
            if (!this.clienteRepository.excluir(id)) {

                return ResponseEntity.status(400).body("Cliente não encontrado.");
            }

            return ResponseEntity.status(200).body("Cliente excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
