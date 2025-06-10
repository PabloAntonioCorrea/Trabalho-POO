package br.csi.util;

import br.csi.dao.AgendamentoDAO;
import br.csi.dao.ClienteDAO;
import br.csi.dao.VeiculoDAO;
import br.csi.model.Agendamento;
import br.csi.model.Cliente;
import br.csi.model.Veiculo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TesteLavaFacil {
    public static void main(String[] args) {
        try {
            // Teste de cadastro de cliente
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = new Cliente("João Silva", "joao@email.com", "123456", "11999999999");
            Long clienteId = clienteDAO.inserir(cliente);
            System.out.println("Cliente cadastrado com ID: " + clienteId);

            // Teste de login
            Cliente clienteLogado = clienteDAO.buscar("joao@email.com", "123456");
            if (clienteLogado != null) {
                System.out.println("Login realizado com sucesso!");
                System.out.println("Bem-vindo, " + clienteLogado.getNome());
            }

            // Teste de cadastro de veículo
            VeiculoDAO veiculoDAO = new VeiculoDAO();
            Veiculo veiculo = new Veiculo("ABC1234", "Carro", "Fiat Uno", clienteId);
            Long veiculoId = veiculoDAO.inserir(veiculo);
            System.out.println("Veículo cadastrado com ID: " + veiculoId);

            // Teste de listagem de veículos do cliente
            ArrayList<Veiculo> veiculos = veiculoDAO.listarPorCliente(clienteId);
            System.out.println("\nVeículos do cliente:");
            for (Veiculo v : veiculos) {
                System.out.println("- " + v.getNome() + " (" + v.getPlaca() + ")");
            }

            // Teste de agendamento
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            Agendamento agendamento = new Agendamento(
                LocalDateTime.now().plusDays(1),
                "COMPLETA",
                50.0,
                veiculoId,
                clienteId
            );
            Long agendamentoId = agendamentoDAO.inserir(agendamento);
            System.out.println("\nAgendamento realizado com ID: " + agendamentoId);

            // Teste de listagem de agendamentos do cliente
            ArrayList<Agendamento> agendamentos = agendamentoDAO.listarPorCliente(clienteId);
            System.out.println("\nAgendamentos do cliente:");
            for (Agendamento a : agendamentos) {
                System.out.println("- " + a.getTipoLavagem() + " em " + a.getDataHora() + 
                                 " (Status: " + a.getStatus() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar operação no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 