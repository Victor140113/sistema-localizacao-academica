package com.api.sla.session;

import com.api.sla.model.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
public class SessaoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer usuarioId;
    private String nome;
    private String perfil;

    public void iniciarSessao(Usuario usuario) {
        this.usuarioId = usuario.getId();
        this.nome = usuario.getNome();
        this.perfil = usuario.getPerfil();
    }

    public void encerrarSessao() {
        usuarioId = null;
        nome = null;
        perfil = null;
    }

    public boolean isAutenticado() {
        return usuarioId != null;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public String getPerfil() {
        return perfil;
    }
}