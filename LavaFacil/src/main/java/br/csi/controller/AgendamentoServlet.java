package br.csi.controller;

import br.csi.dao.AgendamentoDAO;
import br.csi.dao.VeiculoDAO;
import br.csi.model.Agendamento;
import br.csi.model.Veiculo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/agendamento")
public class AgendamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();
        Long clienteId = (Long) session.getAttribute("clienteId");

        if (clienteId == null) {
            response.sendRedirect("/login");
            return;
        }

        switch (acao) {
            case "novo":
                // Carregar a lista de veículos do cliente
                VeiculoDAO veiculoDAO = new VeiculoDAO();
                List<Veiculo> veiculos = veiculoDAO.listarPorCliente(clienteId);
                request.setAttribute("veiculos", veiculos);
                
                // Carregar horários disponíveis
                AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
                List<LocalDateTime> horariosDisponiveis = agendamentoDAO.listarHorariosDisponiveis();
                request.setAttribute("horariosDisponiveis", horariosDisponiveis);
                
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/agendamento.jsp");
                rd.forward(request, response);
                break;

            case "listar":
                // Listar agendamentos do cliente
                AgendamentoDAO dao = new AgendamentoDAO();
                List<Agendamento> agendamentos = dao.listarPorCliente(clienteId);
                request.setAttribute("agendamentos", agendamentos);
                rd = request.getRequestDispatcher("/WEB-INF/views/meus-agendamentos.jsp");
                rd.forward(request, response);
                break;

            case "cancelar":
                Long agendamentoId = Long.parseLong(request.getParameter("id"));
                AgendamentoDAO agDAO = new AgendamentoDAO();
                agDAO.alterarStatus(agendamentoId, "CANCELADO");
                response.sendRedirect("/agendamento?acao=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long clienteId = (Long) session.getAttribute("clienteId");

        if (clienteId == null) {
            response.sendRedirect("/login");
            return;
        }

        // Receber os dados do formulário
        Long veiculoId = Long.parseLong(request.getParameter("veiculo"));
        String dataHoraStr = request.getParameter("dataHora");
        String tipoLavagem = request.getParameter("tipoLavagem");
        
        // Converter a data/hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
        
        // Calcular o valor baseado no tipo de lavagem
        double valor = tipoLavagem.equals("COMPLETA") ? 50.0 : 30.0;

        // Criar o agendamento
        Agendamento agendamento = new Agendamento(dataHora, tipoLavagem, valor, veiculoId, clienteId);
        
        // Salvar no banco
        AgendamentoDAO dao = new AgendamentoDAO();
        dao.inserir(agendamento);

        response.sendRedirect("/agendamento?acao=listar");
    }
} 