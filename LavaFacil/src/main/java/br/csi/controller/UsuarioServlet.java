package br.csi.controller;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao != null && acao.equals("listar")) {
            UsuarioDAO dao = new UsuarioDAO();
            ArrayList<Usuario> usuarios = dao.listar();
            request.setAttribute("usuarios", usuarios);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/usuarios.jsp");
            rd.forward(request, response);
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/cadastro-usuario.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setAtivo(true);
        UsuarioDAO dao = new UsuarioDAO();
        dao.inserir(usuario);
        response.sendRedirect("/appclientes/usuario?acao=listar");
    }
}
