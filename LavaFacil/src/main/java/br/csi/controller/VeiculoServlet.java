package br.csi.controller;

import br.csi.dao.VeiculoDAO;
import br.csi.model.Veiculo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/veiculo")
public class VeiculoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();
        Long clienteId = (Long) session.getAttribute("clienteId");

        if (clienteId == null) {
            response.sendRedirect("/login");
            return;
        }

        VeiculoDAO dao = new VeiculoDAO();

        switch (acao) {
            case "novo":
                request.getRequestDispatcher("/WEB-INF/views/veiculo.jsp").forward(request, response);
                break;

            case "listar":
                request.setAttribute("veiculos", dao.listarPorCliente(clienteId));
                request.getRequestDispatcher("/WEB-INF/views/meus-veiculos.jsp").forward(request, response);
                break;

            case "excluir":
                try {
                    Long id = Long.parseLong(request.getParameter("id"));
                    dao.excluir(id);
                    request.setAttribute("mensagem", "Veículo excluído com sucesso!");
                } catch (Exception e) {
                    request.setAttribute("erro", "Erro ao excluir veículo: " + e.getMessage());
                }
                response.sendRedirect("/veiculo?acao=listar");
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

        String placa = request.getParameter("placa").toUpperCase();
        String tipo = request.getParameter("tipo");
        String nome = request.getParameter("nome");

        Veiculo veiculo = new Veiculo(placa, tipo, nome, clienteId);
        VeiculoDAO dao = new VeiculoDAO();

        try {
            dao.inserir(veiculo);
            request.setAttribute("mensagem", "Veículo cadastrado com sucesso!");
        } catch (SQLException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("veiculo", veiculo);
            request.getRequestDispatcher("/WEB-INF/views/veiculo.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("/veiculo?acao=listar");
    }
} 