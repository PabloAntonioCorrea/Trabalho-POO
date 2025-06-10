package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

import java.util.ArrayList;

public class UsuarioService {

    private final UsuarioDAO dao;

    public UsuarioService() {
        this.dao = new UsuarioDAO();
    }

    public String excluir(Long id) {
        boolean sucesso = dao.desativar(id);

        return sucesso ? "Usuário excluído com sucesso!" : "Erro ao excluir usuário";
    }

    public ArrayList<Usuario> listar() {
        return dao.listar();
    }

    public Usuario buscar(Long usuarioId) {
        return dao.buscarPorId(usuarioId);
    }

    public String inserir(Usuario usuario) {
        Long id = dao.inserir(usuario);

        return id != null ? "Usuário cadastrado com sucesso!" : "Erro ao cadastrar usuário";
    }
}
