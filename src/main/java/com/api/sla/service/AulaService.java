package com.api.sla.service;

import com.api.sla.dao.AulaDAO;
import com.api.sla.dto.AulaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AulaService {

    private final AulaDAO aulaDAO;

    public List<AulaDTO> buscarAulasDoAluno(
            Integer alunoId,
            String diaSemana
    ) {
        return aulaDAO.buscarAulasDoAluno(alunoId, diaSemana);
    }

    public List<AulaDTO> buscarAulasDoProfessor(Integer professorId) {
        return aulaDAO.buscarAulasDoProfessor(professorId);
    }
}