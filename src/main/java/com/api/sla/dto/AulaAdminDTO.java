package com.api.sla.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AulaAdminDTO {

    private Integer id;

    private Integer disciplinaId;
    private String disciplinaNome;

    private Integer professorId;
    private String professorNome;

    private Integer salaId;
    private Integer numeroSala;
    private String bloco;
    private String tipoSala;

    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

}