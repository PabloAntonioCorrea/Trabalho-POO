package br.csi.util;

import br.csi.dao.AgendamentoDAO;
import br.csi.dao.ClienteDAO;
import br.csi.dao.UsuarioDAO;
import br.csi.dao.VeiculoDAO;
import br.csi.model.Agendamento;
import br.csi.model.Cliente;
import br.csi.model.Usuario;
import br.csi.model.Veiculo;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Testes {

    public static void main(String[] args) {
        try {
            testesUsuarioDAO();
            testesClienteDAO();
            testesVeiculoDAO();
            testesAgendamentoDAO();
        } catch (SQLException e) {
            System.err.println("Erro ao executar operação no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testesUsuarioDAO() {
        System.out.println("\n=== Testes UsuarioDAO ===");
        UsuarioDAO dao = new UsuarioDAO();

        // Teste de inserção
        Usuario usuario = new Usuario("Admin", "admin@email.com", "123456");
        Long id = dao.inserir(usuario);
        System.out.println("Usuário inserido com ID: " + id);

        // Teste de busca
        Usuario usuarioEncontrado = dao.buscar("admin@email.com", "123456");
        if (usuarioEncontrado != null) {
            System.out.println("Usuário encontrado: " + usuarioEncontrado.getNome());
        }

        // Teste de listagem
        ArrayList<Usuario> usuarios = dao.listar();
        System.out.println("\nLista de usuários:");
        for (Usuario u : usuarios) {
            System.out.println("- " + u.getNome() + " (" + u.getEmail() + ")");
        }
    }

    public static void testesClienteDAO() {
        System.out.println("\n=== Testes ClienteDAO ===");
        ClienteDAO dao = new ClienteDAO();

        // Teste de inserção
        Cliente cliente = new Cliente("Pablo Corrêa", "pablo@gmail.com", "123456", "55997116485");
        Long id = dao.inserir(cliente);
        System.out.println("Cliente inserido com ID: " + id);

        // Teste de busca
        Cliente clienteEncontrado = dao.buscar("pablo@gmail.com", "123456");
        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado: " + clienteEncontrado.getNome());
        }

        // Teste de listagem
        ArrayList<Cliente> clientes = dao.listar();
        System.out.println("\nLista de clientes:");
        for (Cliente c : clientes) {
            System.out.println("- " + c.getNome() + " (" + c.getEmail() + ")");
        }
    }

    public static void testesVeiculoDAO() throws SQLException {
        System.out.println("\n=== Testes VeiculoDAO ===");
        VeiculoDAO dao = new VeiculoDAO();

        // Primeiro, verifica se o cliente já tem veículos
        ArrayList<Veiculo> veiculosExistentes = dao.listarPorCliente(2L);
        
        if (veiculosExistentes.isEmpty()) {
            // Se não tiver veículos, cadastra um novo
            System.out.println("Cliente não possui veículos. Cadastrando novo veículo...");
            Veiculo veiculo = new Veiculo("ABC1234", "Carro", "Fiat Uno", 2L); // ID 2 é o Pablo
            Long id = dao.inserir(veiculo);
            System.out.println("Veículo inserido com ID: " + id);
        } else {
            // Se já tiver veículos, apenas lista
            System.out.println("Cliente já possui veículos cadastrados:");
        }

        // Lista os veículos do cliente
        ArrayList<Veiculo> veiculos = dao.listarPorCliente(2L);
        for (Veiculo v : veiculos) {
            System.out.println("- " + v.getNome() + " (" + v.getPlaca() + ")");
        }
    }

    public static void testesAgendamentoDAO() {
        System.out.println("\n=== Testes AgendamentoDAO ===");
        AgendamentoDAO dao = new AgendamentoDAO();

        // Teste de inserção - Agendamento para amanhã às 14:00
        LocalDateTime amanha = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0);
        Agendamento agendamento = new Agendamento(
            amanha,
            "COMPLETA", // Tipo de lavagem
            50.0,      // Valor
            1L,        // ID do veículo (Fiat Uno)
            2L         // ID do cliente (Pablo)
        );
        Long id = dao.inserir(agendamento);
        System.out.println("Agendamento inserido com ID: " + id);

        // Teste de listagem de agendamentos do cliente
        ArrayList<Agendamento> agendamentos = dao.listarPorCliente(2L);
        System.out.println("\nAgendamentos do cliente:");
        for (Agendamento a : agendamentos) {
            System.out.println("- " + a.getTipoLavagem() + 
                             " em " + a.getDataHora() + 
                             " (Status: " + a.getStatus() + 
                             ", Valor: R$ " + a.getValor() + ")");
        }
    }

    public static void imprimirUsuario(Usuario usuario) {
        System.out.println(
                "Id: " + usuario.getId() +
                " Nome: " + usuario.getNome() +
                " Email: " + usuario.getEmail() +
                " Ativo: " + usuario.isAtivo());
    }

    public static void imprimirUsuarios(ArrayList<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            imprimirUsuario(usuario);
        }
    }

    public static void imprimirCliente(Cliente cliente) {
        System.out.println(
                "Id: " + cliente.getId() +
                " Nome: " + cliente.getNome() +
                " Email: " + cliente.getEmail() +
                " Telefone: " + cliente.getTelefone() +
                " Ativo: " + cliente.isAtivo());
    }

    public static void imprimir(ArrayList<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            imprimirCliente(cliente);
        }
    }

}
