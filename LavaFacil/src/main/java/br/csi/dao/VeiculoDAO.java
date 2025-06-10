package br.csi.dao;

import br.csi.model.Veiculo;
import br.csi.util.ConectaDB;

import java.sql.*;
import java.util.ArrayList;

public class VeiculoDAO {
    private String SQL_INSERIR = "INSERT INTO veiculo(placa, tipo, nome, cliente_id) VALUES(?, ?, ?, ?)";
    private String SQL_BUSCAR = "SELECT * FROM veiculo WHERE id = ?";
    private String SQL_LISTAR_POR_CLIENTE = "SELECT * FROM veiculo WHERE cliente_id = ?";
    private String SQL_ALTERAR = "UPDATE veiculo SET placa = ?, tipo = ?, nome = ? WHERE id = ?";
    private String SQL_EXCLUIR = "DELETE FROM veiculo WHERE id = ?";
    private String SQL_VERIFICAR_PLACA = "SELECT COUNT(*) FROM veiculo WHERE placa = ?";

    public Long inserir(Veiculo veiculo) throws SQLException {
        // Primeiro verificar se a placa já existe
        if (verificarPlacaExistente(veiculo.getPlaca())) {
            throw new SQLException("Já existe um veículo cadastrado com esta placa: " + veiculo.getPlaca());
        }

        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, veiculo.getPlaca());
            pstmt.setString(2, veiculo.getTipo());
            pstmt.setString(3, veiculo.getNome());
            pstmt.setLong(4, veiculo.getClienteId());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return null;
    }

    private boolean verificarPlacaExistente(String placa) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_VERIFICAR_PLACA)) {
            pstmt.setString(1, placa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Veiculo buscar(Long id) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Veiculo veiculo = new Veiculo();
                    veiculo.setId(rs.getLong("id"));
                    veiculo.setPlaca(rs.getString("placa"));
                    veiculo.setTipo(rs.getString("tipo"));
                    veiculo.setNome(rs.getString("nome"));
                    veiculo.setClienteId(rs.getLong("cliente_id"));
                    return veiculo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Veiculo> listarPorCliente(Long clienteId) {
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_LISTAR_POR_CLIENTE)) {
            pstmt.setLong(1, clienteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Veiculo veiculo = new Veiculo();
                    veiculo.setId(rs.getLong("id"));
                    veiculo.setPlaca(rs.getString("placa"));
                    veiculo.setTipo(rs.getString("tipo"));
                    veiculo.setNome(rs.getString("nome"));
                    veiculo.setClienteId(rs.getLong("cliente_id"));
                    veiculos.add(veiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    public boolean alterar(Veiculo veiculo) throws SQLException {
        // Verificar se a nova placa já existe em outro veículo
        if (verificarPlacaExistente(veiculo.getPlaca())) {
            // Buscar o veículo atual para verificar se a placa pertence a ele mesmo
            Veiculo veiculoAtual = buscar(veiculo.getId());
            if (veiculoAtual == null || !veiculoAtual.getPlaca().equals(veiculo.getPlaca())) {
                throw new SQLException("Já existe um veículo cadastrado com esta placa: " + veiculo.getPlaca());
            }
        }

        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_ALTERAR)) {
            pstmt.setString(1, veiculo.getPlaca());
            pstmt.setString(2, veiculo.getTipo());
            pstmt.setString(3, veiculo.getNome());
            pstmt.setLong(4, veiculo.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_EXCLUIR)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 