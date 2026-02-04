package com.nca.api_clientes.factories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class ConnectionFactory {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private  String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public Connection getConnection() {
        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}
