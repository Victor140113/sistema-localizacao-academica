package com.api.sla.controller;

import com.api.sla.model.Usuario;
import com.api.sla.service.UsuarioService;
import com.api.sla.session.SessaoUsuario;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@Controller("loginController")
@RequestScope
public class LoginController implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UsuarioService usuarioService;
    private final SessaoUsuario sessaoUsuario;

    private String email;
    private String senha;

    public LoginController(UsuarioService usuarioService, SessaoUsuario sessaoUsuario) {
        this.usuarioService = usuarioService;
        this.sessaoUsuario = sessaoUsuario;
    }

    public String entrar() {

        Optional<Usuario> usuarioOptional = usuarioService.autenticar(email, senha);

        if (usuarioOptional.isEmpty()) {

            sessaoUsuario.encerrarSessao();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "E-mail ou senha inválidos.", null));

            return null;
        }

        Usuario usuario = usuarioOptional.get();

        sessaoUsuario.iniciarSessao(usuario);

        if ("ALUNO".equals(usuario.getPerfil())) {
            return "/aluno/aluno.xhtml?faces-redirect=true";
        }

        if ("PROFESSOR".equals(usuario.getPerfil())) {
            return "/professor/professor.xhtml?faces-redirect=true";
        }

        sessaoUsuario.encerrarSessao();

        return null;
    }
}