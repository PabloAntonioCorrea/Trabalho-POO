package br.csi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/lava_facil";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123654123";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado", e);
        }
    }
} 