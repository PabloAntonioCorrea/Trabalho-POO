package br.csi.dao;

import br.csi.model.Usuario;
import br.csi.util.ConectaDB;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    private String SQL_INSERIR = "INSERT INTO usuario(nome, email, senha, ativo) VALUES(?, ?, ?, ?)";
    private String SQL_BUSCAR = "SELECT * FROM usuario WHERE email = ? AND senha = ? AND ativo = true";
    private String SQL_BUSCAR_POR_ID = "SELECT * FROM usuario WHERE id = ?";
    private String SQL_LISTAR = "SELECT * FROM usuario WHERE ativo = true";
    private String SQL_ALTERAR = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
    private String SQL_DESATIVAR = "UPDATE usuario SET ativo = false WHERE id = ?";

    public Long inserir(Usuario usuario) {
        // Inserir um novo usuário no banco de dados
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERIR, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setBoolean(4, usuario.isAtivo());

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

    public Usuario buscar(String email, String senha) {
        // Buscar um usuário pelo email e senha
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario buscarPorId(Long id) {
        // Buscar um usuário pelo ID
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {

            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setAtivo(rs.getBoolean("ativo"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Usuario> listar() {
        // Listar todos os usuários ativos
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setAtivo(rs.getBoolean("ativo"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public boolean alterar(Usuario usuario) {
        // Alterar os dados de um usuário
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_ALTERAR)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());
            pstmt.setLong(4, usuario.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean desativar(Long id) {
        // Desativar um usuário (exclusão lógica)
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
