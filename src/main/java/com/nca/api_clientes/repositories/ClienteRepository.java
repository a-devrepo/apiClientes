package com.nca.api_clientes.repositories;

import com.nca.api_clientes.dtos.ClienteRequest;
import com.nca.api_clientes.factories.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ClienteRepository  {

    private ConnectionFactory connectionFactory;

    public ClienteRepository(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    public void salvar(ClienteRequest cliente) {

        try (var connection = connectionFactory.getConnection()) {

            var sql = """
                    INSERT INTO clientes (id, nome, email, telefone)
                     VALUES (?, ?, ?, ?)""";

            var statement = connection.prepareStatement(sql);

            statement.setObject(1, UUID.randomUUID());
            statement.setString(2, cliente.nome());
            statement.setString(3, cliente.email());
            statement.setString(4, cliente.telefone());

            statement.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir cliente", e);
        }
    }
}
