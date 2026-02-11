package com.nca.api_clientes.repositories;

import com.nca.api_clientes.dtos.ClienteRequest;
import com.nca.api_clientes.entities.Cliente;
import com.nca.api_clientes.factories.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class ClienteRepository {

    private ConnectionFactory connectionFactory;

    public ClienteRepository(ConnectionFactory connectionFactory) {
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

    public List<Cliente> obterPorNome(String nome) throws SQLException {

        try (var connection = connectionFactory.getConnection()) {

            var sql = """
                    SELECT id, nome, email, telefone, datacadastro, ativo
                        FROM clientes
                     WHERE ativo = 1
                       AND nome ILIKE ?""";

            var statement = connection.prepareStatement(sql);

            statement.setString(1, "%" + nome + "%");

            var clientesResultSet = statement.executeQuery();

            var clientes = new ArrayList<Cliente>();

            while (clientesResultSet.next()) {
                clientes.add(new Cliente(
                        (UUID) clientesResultSet.getObject("id"),
                        clientesResultSet.getString("nome"),
                        clientesResultSet.getString("email"),
                        clientesResultSet.getString("telefone"),
                        new Date(clientesResultSet.getTimestamp("datacadastro").getTime()),
                        true));
            }

            return clientes;

        } catch (SQLException e) {
            throw new SQLException("Erro ao obter por nome: ", e);
        }
    }
}
