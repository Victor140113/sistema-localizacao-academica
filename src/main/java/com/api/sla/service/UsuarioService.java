package com.api.sla.service;

import com.api.sla.dao.UsuarioDAO;
import com.api.sla.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public Optional<Usuario> autenticar(String email, String senha) {

        Optional<Usuario> usuarioOptional =
                usuarioDAO.buscarPorEmail(email);

        if (usuarioOptional.isEmpty()) {
            return Optional.empty();
        }

        Usuario usuario = usuarioOptional.get();

        if (!usuario.getSenha().equals(senha)) {
            return Optional.empty();
        }

        return Optional.of(usuario);
    }
}