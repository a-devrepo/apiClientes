package com.nca.api_clientes.controllers;

import com.nca.api_clientes.dtos.ClienteRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {


    @PostMapping
    public void cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {

    }
}
