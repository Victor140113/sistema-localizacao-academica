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
public class AulaDTO {

    private String disciplina;
    private String diaSemana;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String bloco;
    private Integer numeroSala;
    private String tipoSala;
}
