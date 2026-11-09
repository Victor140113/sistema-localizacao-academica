package com.api.sla.controller;

import com.api.sla.dto.AulaDTO;
import com.api.sla.service.AulaService;
import com.api.sla.session.SessaoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Getter
@AllArgsConstructor
@Controller("professorController")
@RequestScope
public class ProfessorController {

    private final AulaService aulaService;
    private final SessaoUsuario sessaoUsuario;

    private List<AulaDTO> aulas = List.of();

    public String carregar() {

        if (!temAcesso()) {
            return "/index.xhtml?faces-redirect=true";
        }

        aulas = aulaService.buscarAulasDoProfessor(sessaoUsuario.getUsuarioId());

        return null;
    }

    private boolean temAcesso() {
        return sessaoUsuario.isAutenticado() && "PROFESSOR".equals(sessaoUsuario.getPerfil());
    }

    public String getNomeProfessor() {
        return sessaoUsuario.getNome();
    }

    public String nomeDia(String diaSemana) {

        return switch (diaSemana) {
            case "SEGUNDA" -> "Segunda-feira";
            case "TERCA" -> "Terça-feira";
            case "QUARTA" -> "Quarta-feira";
            case "QUINTA" -> "Quinta-feira";
            case "SEXTA" -> "Sexta-feira";
            case "SABADO" -> "Sábado";
            case "DOMINGO" -> "Domingo";
            default -> diaSemana;
        };
    }
}