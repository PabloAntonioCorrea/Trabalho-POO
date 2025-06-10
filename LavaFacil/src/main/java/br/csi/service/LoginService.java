package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

public class LoginService {

    public boolean autenticar(String email, String senha) {
        UsuarioDAO dao = new UsuarioDAO();

        Usuario usuario = dao.buscar(email, senha);

        return usuario != null;
    }

}
