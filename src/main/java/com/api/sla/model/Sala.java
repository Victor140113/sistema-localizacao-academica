package com.api.sla.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    private Integer id;
    private Integer numero;
    private String bloco;
    private String tipo;

}