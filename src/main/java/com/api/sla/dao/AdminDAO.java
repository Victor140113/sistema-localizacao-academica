package com.api.sla.dao;

import com.api.sla.dto.AulaAdminDTO;
import com.api.sla.dto.UsuarioResumoDTO;
import com.api.sla.model.Disciplina;
import com.api.sla.model.Sala;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class AdminDAO {

    private final JdbcTemplate jdbcTemplate;

    // =====================================================
    // PROFESSORES
    // =====================================================

    private final RowMapper<UsuarioResumoDTO> professorMapper =
            (rs, rowNum) -> new UsuarioResumoDTO(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email")
            );

    public List<UsuarioResumoDTO> listarProfessores() {

        String sql = """
                SELECT id, nome, email
                FROM usuario
                WHERE perfil = 'PROFESSOR'
                ORDER BY nome
                """;

        return jdbcTemplate.query(sql, professorMapper);
    }

    public boolean existeEmail(String email, Integer idIgnorado) {

        String sql;

        if (idIgnorado == null) {
            sql = """
                SELECT COUNT(*)
                FROM usuario
                WHERE LOWER(email) = LOWER(?)
                """;

            Integer quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    email
            );

            return quantidade != null && quantidade > 0;
        }

        sql = """
            SELECT COUNT(*)
            FROM usuario
            WHERE LOWER(email) = LOWER(?)
              AND id <> ?
            """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email,
                idIgnorado
        );

        return quantidade != null && quantidade > 0;
    }

    public boolean professorExiste(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM usuario
                WHERE id = ?
                  AND perfil = 'PROFESSOR'
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public void inserirProfessor(
            String nome,
            String email,
            String senha
    ) {

        String sql = """
                INSERT INTO usuario (nome, email, senha, perfil)
                VALUES (?, ?, ?, 'PROFESSOR')
                """;

        jdbcTemplate.update(sql, nome, email, senha);
    }

    public void atualizarProfessor(
            Integer id,
            String nome,
            String email
    ) {

        String sql = """
                UPDATE usuario
                SET nome = ?, email = ?
                WHERE id = ?
                  AND perfil = 'PROFESSOR'
                """;

        jdbcTemplate.update(sql, nome, email, id);
    }

    public boolean professorPossuiAulas(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM aula
                WHERE professor_id = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public void excluirProfessor(Integer id) {

        String sql = """
                DELETE FROM usuario
                WHERE id = ?
                  AND perfil = 'PROFESSOR'
                """;

        jdbcTemplate.update(sql, id);
    }

    // =====================================================
    // DISCIPLINAS
    // =====================================================

    private final RowMapper<Disciplina> disciplinaMapper =
            (rs, rowNum) -> new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome")
            );

    public List<Disciplina> listarDisciplinas() {

        String sql = """
                SELECT id, nome
                FROM disciplina
                ORDER BY nome
                """;

        return jdbcTemplate.query(sql, disciplinaMapper);
    }

    public boolean disciplinaExiste(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM disciplina
                WHERE id = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public boolean existeNomeDisciplina(
            String nome,
            Integer idIgnorado
    ) {

        String sql;
        Integer quantidade;

        if (idIgnorado == null) {
            sql = """
                SELECT COUNT(*)
                FROM disciplina
                WHERE LOWER(nome) = LOWER(?)
                """;

            quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    nome
            );
        } else {
            sql = """
                SELECT COUNT(*)
                FROM disciplina
                WHERE LOWER(nome) = LOWER(?)
                  AND id <> ?
                """;

            quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    nome,
                    idIgnorado
            );
        }

        return quantidade != null && quantidade > 0;
    }

    public void inserirDisciplina(String nome) {

        String sql = """
                INSERT INTO disciplina (nome)
                VALUES (?)
                """;

        jdbcTemplate.update(sql, nome);
    }

    public void atualizarDisciplina(
            Integer id,
            String nome
    ) {

        String sql = """
                UPDATE disciplina
                SET nome = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, nome, id);
    }

    public boolean disciplinaPossuiVinculos(Integer id) {

        String sql = """
                SELECT
                    (
                        SELECT COUNT(*)
                        FROM aula
                        WHERE disciplina_id = ?
                    )
                    +
                    (
                        SELECT COUNT(*)
                        FROM aluno_disciplina
                        WHERE disciplina_id = ?
                    )
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public void excluirDisciplina(Integer id) {

        String sql = """
                DELETE FROM disciplina
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    // =====================================================
    // SALAS
    // =====================================================

    private final RowMapper<Sala> salaMapper =
            (rs, rowNum) -> new Sala(
                    rs.getInt("id"),
                    rs.getInt("numero"),
                    rs.getString("bloco"),
                    rs.getString("tipo")
            );

    public List<Sala> listarSalas() {

        String sql = """
                SELECT id, numero, bloco, tipo
                FROM sala
                ORDER BY bloco, numero
                """;

        return jdbcTemplate.query(sql, salaMapper);
    }

    public boolean salaExiste(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM sala
                WHERE id = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public boolean existeSala(
            String bloco,
            Integer numero,
            Integer idIgnorado
    ) {

        String sql;
        Integer quantidade;

        if (idIgnorado == null) {
            sql = """
                SELECT COUNT(*)
                FROM sala
                WHERE LOWER(bloco) = LOWER(?)
                  AND numero = ?
                """;

            quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    bloco,
                    numero
            );
        } else {
            sql = """
                SELECT COUNT(*)
                FROM sala
                WHERE LOWER(bloco) = LOWER(?)
                  AND numero = ?
                  AND id <> ?
                """;

            quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    bloco,
                    numero,
                    idIgnorado
            );
        }

        return quantidade != null && quantidade > 0;
    }

    public void inserirSala(
            Integer numero,
            String bloco,
            String tipo
    ) {

        String sql = """
                INSERT INTO sala (numero, bloco, tipo)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql, numero, bloco, tipo);
    }

    public void atualizarSala(
            Integer id,
            Integer numero,
            String bloco,
            String tipo
    ) {

        String sql = """
                UPDATE sala
                SET numero = ?, bloco = ?, tipo = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, numero, bloco, tipo, id);
    }

    public boolean salaPossuiAulas(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM aula
                WHERE sala_id = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

    public void excluirSala(Integer id) {

        String sql = """
                DELETE FROM sala
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    // =====================================================
    // AULAS / ALOCAÇÕES
    // =====================================================

    private final RowMapper<AulaAdminDTO> aulaAdminMapper =
            (rs, rowNum) -> {

                AulaAdminDTO aula = new AulaAdminDTO();

                aula.setId(rs.getInt("id"));

                aula.setDisciplinaId(
                        rs.getInt("disciplina_id")
                );

                aula.setDisciplinaNome(
                        rs.getString("disciplina_nome")
                );

                aula.setProfessorId(
                        rs.getInt("professor_id")
                );

                aula.setProfessorNome(
                        rs.getString("professor_nome")
                );

                aula.setSalaId(
                        rs.getInt("sala_id")
                );

                aula.setNumeroSala(
                        rs.getInt("numero_sala")
                );

                aula.setBloco(
                        rs.getString("bloco")
                );

                aula.setTipoSala(
                        rs.getString("tipo_sala")
                );

                aula.setDiaSemana(
                        rs.getString("dia_semana")
                );

                aula.setHorarioInicio(
                        rs.getTime("horario_inicio").toLocalTime()
                );

                aula.setHorarioFim(
                        rs.getTime("horario_fim").toLocalTime()
                );

                return aula;
            };

    public List<AulaAdminDTO> listarAulas() {

        String sql = """
                SELECT
                    a.id,
                    a.disciplina_id,
                    d.nome AS disciplina_nome,
                    a.professor_id,
                    u.nome AS professor_nome,
                    a.sala_id,
                    s.numero AS numero_sala,
                    s.bloco,
                    s.tipo AS tipo_sala,
                    a.dia_semana,
                    a.horario_inicio,
                    a.horario_fim
                FROM aula a
                INNER JOIN disciplina d
                    ON d.id = a.disciplina_id
                INNER JOIN usuario u
                    ON u.id = a.professor_id
                INNER JOIN sala s
                    ON s.id = a.sala_id
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

        return jdbcTemplate.query(sql, aulaAdminMapper);
    }

    public boolean existeConflitoProfessor(
            Integer professorId,
            String diaSemana,
            LocalTime inicio,
            LocalTime fim,
            Integer aulaId
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM aula
                WHERE professor_id = ?
                  AND dia_semana = ?
                  AND horario_inicio < ?
                  AND horario_fim > ?
                """ + (aulaId == null ? "" : " AND id <> ?");

        if (aulaId == null) {
            Integer quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    professorId,
                    diaSemana,
                    Time.valueOf(fim),
                    Time.valueOf(inicio)
            );

            return quantidade != null && quantidade > 0;
        }

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                professorId,
                diaSemana,
                Time.valueOf(fim),
                Time.valueOf(inicio),
                aulaId
        );

        return quantidade != null && quantidade > 0;
    }

    public boolean existeConflitoSala(
            Integer salaId,
            String diaSemana,
            LocalTime inicio,
            LocalTime fim,
            Integer aulaId
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM aula
                WHERE sala_id = ?
                  AND dia_semana = ?
                  AND horario_inicio < ?
                  AND horario_fim > ?
                """ + (aulaId == null ? "" : " AND id <> ?");

        if (aulaId == null) {
            Integer quantidade = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    salaId,
                    diaSemana,
                    Time.valueOf(fim),
                    Time.valueOf(inicio)
            );

            return quantidade != null && quantidade > 0;
        }

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                salaId,
                diaSemana,
                Time.valueOf(fim),
                Time.valueOf(inicio),
                aulaId
        );

        return quantidade != null && quantidade > 0;
    }

    public void inserirAula(
            Integer disciplinaId,
            Integer professorId,
            Integer salaId,
            String diaSemana,
            LocalTime inicio,
            LocalTime fim
    ) {

        String sql = """
                INSERT INTO aula (
                    disciplina_id,
                    professor_id,
                    sala_id,
                    dia_semana,
                    horario_inicio,
                    horario_fim
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                disciplinaId,
                professorId,
                salaId,
                diaSemana,
                Time.valueOf(inicio),
                Time.valueOf(fim)
        );
    }

    public void atualizarAula(
            Integer id,
            Integer disciplinaId,
            Integer professorId,
            Integer salaId,
            String diaSemana,
            LocalTime inicio,
            LocalTime fim
    ) {

        String sql = """
                UPDATE aula
                SET disciplina_id = ?,
                    professor_id = ?,
                    sala_id = ?,
                    dia_semana = ?,
                    horario_inicio = ?,
                    horario_fim = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                disciplinaId,
                professorId,
                salaId,
                diaSemana,
                Time.valueOf(inicio),
                Time.valueOf(fim),
                id
        );
    }

    public void excluirAula(Integer id) {

        String sql = """
                DELETE FROM aula
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    public boolean aulaExiste(Integer id) {

        String sql = """
                SELECT COUNT(*)
                FROM aula
                WHERE id = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                id
        );

        return quantidade != null && quantidade > 0;
    }

}