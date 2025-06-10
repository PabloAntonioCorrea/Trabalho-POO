package br.csi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBancoDados {

    public static Connection conectarBancoPostgres()
            throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        System.out.println("Driver carregado");
        String url = "jdbc:postgresql://localhost:5432/lava_facil";
        String user = "postgres";
        String senha = "1234";
        Connection conn =
                DriverManager.getConnection(url, user, senha);
        return conn;
    }

    public Connection conectarBancoPostgresMySql(){
        return null;
    }
}
