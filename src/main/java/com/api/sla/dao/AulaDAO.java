package com.api.sla.dao;

import com.api.sla.dto.AulaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Repository
public class AulaDAO {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AulaDTO> aulaMapper = (rs, rowNum) -> {
        AulaDTO aula = new AulaDTO();

        aula.setDisciplina(rs.getString("disciplina"));
        aula.setDiaSemana(rs.getString("dia_semana"));
        aula.setHorarioInicio(
                rs.getTime("horario_inicio").toLocalTime()
        );
        aula.setHorarioFim(
                rs.getTime("horario_fim").toLocalTime()
        );
        aula.setBloco(rs.getString("bloco"));
        aula.setNumeroSala(rs.getInt("numero_sala"));
        aula.setTipoSala(rs.getString("tipo_sala"));

        return aula;
    };

    public List<AulaDTO> buscarAulasDoAluno(
            Integer alunoId,
            String diaSemana) {

        String sql = """
                SELECT
                    d.nome AS disciplina,
                    a.dia_semana,
                    a.horario_inicio,
                    a.horario_fim,
                    s.bloco,
                    s.numero AS numero_sala,
                    s.tipo AS tipo_sala
                
                FROM aluno_disciplina ad
                
                INNER JOIN disciplina d
                    ON d.id = ad.disciplina_id
                
                INNER JOIN alocacao a
                    ON a.disciplina_id = d.id
                
                INNER JOIN sala s
                    ON s.id = a.sala_id
                
                WHERE ad.aluno_id = ?
                AND a.dia_semana = ?
                
                ORDER BY a.horario_inicio
                """;

        return jdbcTemplate.query(
                sql,
                aulaMapper,
                alunoId,
                diaSemana
        );
    }

    public List<AulaDTO> buscarAlocacoesDoProfessor(
            Integer professorId) {

        String sql = """
                SELECT
                    d.nome AS disciplina,
                    a.dia_semana,
                    a.horario_inicio,
                    a.horario_fim,
                    s.bloco,
                    s.numero AS numero_sala,
                    s.tipo AS tipo_sala
                
                FROM alocacao a
                
                INNER JOIN disciplina d
                    ON d.id = a.disciplina_id
                
                INNER JOIN sala s
                    ON s.id = a.sala_id
                
                WHERE a.professor_id = ?
                
                ORDER BY
                    CASE a.dia_semana
                        WHEN 'SEGUNDA' THEN 1
                        WHEN 'TERCA' THEN 2
                        WHEN 'QUARTA' THEN 3
                        WHEN 'QUINTA' THEN 4
                        WHEN 'SEXTA' THEN 5
                        WHEN 'SABADO' THEN 6
                        WHEN 'DOMINGO' THEN 7
                    END,
                    a.horario_inicio
                """;

        return jdbcTemplate.query(
                sql,
                aulaMapper,
                professorId
        );
    }
}
