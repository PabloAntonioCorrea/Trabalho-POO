package br.csi.dao;

import br.csi.model.Cliente;
import br.csi.util.ConectaDB;

import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {
    private String SQL_INSERIR = "INSERT INTO cliente(nome, email, senha, telefone, ativo) VALUES(?, ?, ?, ?, ?)";
    private String SQL_BUSCAR = "SELECT * FROM cliente WHERE email = ? AND senha = ? AND ativo = true";
    private String SQL_BUSCAR_POR_ID = "SELECT * FROM cliente WHERE id = ?";
    private String SQL_LISTAR = "SELECT * FROM cliente WHERE ativo = true";
    private String SQL_ALTERAR = "UPDATE cliente SET nome = ?, email = ?, senha = ?, telefone = ? WHERE id = ?";
    private String SQL_DESATIVAR = "UPDATE cliente SET ativo = false WHERE id = ?";

    public Long inserir(Cliente cliente) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getSenha());
            pstmt.setString(4, cliente.getTelefone());
            pstmt.setBoolean(5, cliente.isAtivo());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente buscar(String email, String senha) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR)) {
            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setAtivo(rs.getBoolean("ativo"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente buscarPorId(Long id) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setAtivo(rs.getBoolean("ativo"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setAtivo(rs.getBoolean("ativo"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean alterar(Cliente cliente) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_ALTERAR)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getSenha());
            pstmt.setString(4, cliente.getTelefone());
            pstmt.setLong(5, cliente.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean desativar(Long id) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DESATIVAR)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
