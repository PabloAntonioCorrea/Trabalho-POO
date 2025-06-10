package br.csi.dao;

import br.csi.model.Agendamento;
import br.csi.util.ConectaDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {
    private String SQL_INSERIR = "INSERT INTO agendamento(data_hora, tipo_lavagem, valor, status, veiculo_id, cliente_id) VALUES(?, ?, ?, ?, ?, ?)";
    private String SQL_BUSCAR = "SELECT * FROM agendamento WHERE id = ?";
    private String SQL_LISTAR_POR_CLIENTE = "SELECT * FROM agendamento WHERE cliente_id = ? ORDER BY data_hora DESC";
    private String SQL_ALTERAR_STATUS = "UPDATE agendamento SET status = ? WHERE id = ?";
    private String SQL_EXCLUIR = "DELETE FROM agendamento WHERE id = ?";
    private String SQL_VERIFICAR_DISPONIBILIDADE = "SELECT COUNT(*) FROM agendamento WHERE data_hora = ? AND status != 'CANCELADO'";

    public Long inserir(Agendamento agendamento) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERIR, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(agendamento.getDataHora()));
            pstmt.setString(2, agendamento.getTipoLavagem());
            pstmt.setDouble(3, agendamento.getValor());
            pstmt.setString(4, agendamento.getStatus());
            pstmt.setLong(5, agendamento.getVeiculoId());
            pstmt.setLong(6, agendamento.getClienteId());
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

    public Agendamento buscar(Long id) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_BUSCAR)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(rs.getLong("id"));
                    agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    agendamento.setTipoLavagem(rs.getString("tipo_lavagem"));
                    agendamento.setValor(rs.getDouble("valor"));
                    agendamento.setStatus(rs.getString("status"));
                    agendamento.setVeiculoId(rs.getLong("veiculo_id"));
                    agendamento.setClienteId(rs.getLong("cliente_id"));
                    return agendamento;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Agendamento> listarPorCliente(Long clienteId) {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_LISTAR_POR_CLIENTE)) {
            pstmt.setLong(1, clienteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(rs.getLong("id"));
                    agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    agendamento.setTipoLavagem(rs.getString("tipo_lavagem"));
                    agendamento.setValor(rs.getDouble("valor"));
                    agendamento.setStatus(rs.getString("status"));
                    agendamento.setVeiculoId(rs.getLong("veiculo_id"));
                    agendamento.setClienteId(rs.getLong("cliente_id"));
                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }

    public boolean alterarStatus(Long id, String novoStatus) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_ALTERAR_STATUS)) {
            pstmt.setString(1, novoStatus);
            pstmt.setLong(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    public List<LocalDateTime> listarHorariosDisponiveis() {
        List<LocalDateTime> horariosDisponiveis = new ArrayList<>();
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime fimDoDia = agora.with(LocalTime.of(18, 0)); // Horário de fechamento: 18:00
        
        // Gerar horários disponíveis para os próximos 7 dias
        for (int dia = 0; dia < 7; dia++) {
            LocalDateTime dataAtual = agora.plusDays(dia).with(LocalTime.of(8, 0)); // Horário de abertura: 8:00
            
            while (dataAtual.isBefore(fimDoDia)) {
                if (verificarDisponibilidade(dataAtual)) {
                    horariosDisponiveis.add(dataAtual);
                }
                dataAtual = dataAtual.plusMinutes(30); // Intervalos de 30 minutos
            }
        }
        
        return horariosDisponiveis;
    }

    private boolean verificarDisponibilidade(LocalDateTime dataHora) {
        try (Connection conn = ConectaDB.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(SQL_VERIFICAR_DISPONIBILIDADE)) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(dataHora));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0; // Retorna true se não houver agendamentos neste horário
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 