package com.api.sla.controller;

import com.api.sla.dto.AulaDTO;
import com.api.sla.service.AulaService;
import com.api.sla.session.SessaoUsuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Getter
@Setter
@Controller("alunoController")
@RequestScope
public class AlunoController {

    private final AulaService aulaService;
    private final SessaoUsuario sessaoUsuario;

    private String diaSemana = "SEGUNDA";
    private List<AulaDTO> aulas = List.of();

    public AlunoController(
            AulaService aulaService,
            SessaoUsuario sessaoUsuario
    ) {
        this.aulaService = aulaService;
        this.sessaoUsuario = sessaoUsuario;
    }

    public String carregar() {

        if (!temAcesso()) {
            return "/index.xhtml?faces-redirect=true";
        }

        consultarDados();

        return null;
    }

    public String consultarAulas() {

        if (!temAcesso()) {
            return "/index.xhtml?faces-redirect=true";
        }

        consultarDados();

        return null;
    }

    private void consultarDados() {
        aulas = aulaService.buscarAulasDoAluno(
                sessaoUsuario.getUsuarioId(),
                diaSemana
        );
    }

    private boolean temAcesso() {
        return sessaoUsuario.isAutenticado()
                && "ALUNO".equals(sessaoUsuario.getPerfil());
    }

    public String getNomeAluno() {
        return sessaoUsuario.getNome();
    }
}