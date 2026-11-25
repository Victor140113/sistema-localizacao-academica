package com.api.sla.service;

import com.api.sla.dao.AdminDAO;
import com.api.sla.dto.AulaAdminDTO;
import com.api.sla.dto.UsuarioResumoDTO;
import com.api.sla.model.Disciplina;
import com.api.sla.model.Sala;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class AdminService {

    private final AdminDAO adminDAO;

    private static final Set<String> DIAS_VALIDOS = Set.of(
            "SEGUNDA",
            "TERCA",
            "QUARTA",
            "QUINTA",
            "SEXTA",
            "SABADO",
            "DOMINGO"
    );

    private static final Set<String> TIPOS_SALA = Set.of(
            "TEORICA",
            "PRATICA"
    );

    // =====================================================
    // PROFESSORES
    // =====================================================

    public List<UsuarioResumoDTO> listarProfessores() {
        return adminDAO.listarProfessores();
    }

    public void salvarProfessor(
            Integer id,
            String nome,
            String email,
            String senha
    ) {

        validarTexto(nome, "Informe o nome do professor.");
        validarTexto(email, "Informe o e-mail do professor.");

        email = email.trim().toLowerCase();

        if (!email.contains("@")) {
            throw new IllegalArgumentException(
                    "Informe um e-mail válido."
            );
        }

        if (adminDAO.existeEmail(email, id)) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com este e-mail."
            );
        }

        if (id == null) {

            validarTexto(senha, "Informe a senha do professor.");

            adminDAO.inserirProfessor(
                    nome.trim(),
                    email,
                    senha
            );

            return;
        }

        if (!adminDAO.professorExiste(id)) {
            throw new IllegalArgumentException(
                    "Professor não encontrado."
            );
        }

        adminDAO.atualizarProfessor(
                id,
                nome.trim(),
                email
        );
    }

    public void excluirProfessor(Integer id) {

        validarId(id);

        if (!adminDAO.professorExiste(id)) {
            throw new IllegalArgumentException(
                    "Professor não encontrado."
            );
        }

        if (adminDAO.professorPossuiAulas(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir este professor porque ele possui aulas cadastradas."
            );
        }

        adminDAO.excluirProfessor(id);
    }

    // =====================================================
    // DISCIPLINAS
    // =====================================================

    public List<Disciplina> listarDisciplinas() {
        return adminDAO.listarDisciplinas();
    }

    public void salvarDisciplina(
            Integer id,
            String nome
    ) {

        validarTexto(nome, "Informe o nome da disciplina.");

        nome = nome.trim();

        if (adminDAO.existeNomeDisciplina(nome, id)) {
            throw new IllegalArgumentException(
                    "Já existe uma disciplina com este nome."
            );
        }

        if (id == null) {
            adminDAO.inserirDisciplina(nome);
            return;
        }

        if (!adminDAO.disciplinaExiste(id)) {
            throw new IllegalArgumentException(
                    "Disciplina não encontrada."
            );
        }

        adminDAO.atualizarDisciplina(id, nome);
    }

    public void excluirDisciplina(Integer id) {

        validarId(id);

        if (!adminDAO.disciplinaExiste(id)) {
            throw new IllegalArgumentException(
                    "Disciplina não encontrada."
            );
        }

        if (adminDAO.disciplinaPossuiVinculos(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir esta disciplina porque existem aulas ou alunos vinculados a ela."
            );
        }

        adminDAO.excluirDisciplina(id);
    }

    // =====================================================
    // SALAS
    // =====================================================

    public List<Sala> listarSalas() {
        return adminDAO.listarSalas();
    }

    public void salvarSala(
            Integer id,
            Integer numero,
            String bloco,
            String tipo
    ) {

        validarId(numero);
        validarTexto(bloco, "Informe o bloco.");

        bloco = bloco.trim().toUpperCase();

        if (!TIPOS_SALA.contains(tipo)) {
            throw new IllegalArgumentException(
                    "Tipo de sala inválido."
            );
        }

        if (adminDAO.existeSala(bloco, numero, id)) {
            throw new IllegalArgumentException(
                    "Já existe uma sala com este bloco e número."
            );
        }

        if (id == null) {
            adminDAO.inserirSala(numero, bloco, tipo);
            return;
        }

        if (!adminDAO.salaExiste(id)) {
            throw new IllegalArgumentException(
                    "Sala não encontrada."
            );
        }

        adminDAO.atualizarSala(
                id,
                numero,
                bloco,
                tipo
        );
    }

    public void excluirSala(Integer id) {

        validarId(id);

        if (!adminDAO.salaExiste(id)) {
            throw new IllegalArgumentException(
                    "Sala não encontrada."
            );
        }

        if (adminDAO.salaPossuiAulas(id)) {
            throw new IllegalArgumentException(
                    "Não é possível excluir esta sala porque existem aulas vinculadas a ela."
            );
        }

        adminDAO.excluirSala(id);
    }

    // =====================================================
    // AULAS / ALOCAÇÕES
    // =====================================================

    public List<AulaAdminDTO> listarAulas() {
        return adminDAO.listarAulas();
    }

    public void salvarAula(
            Integer id,
            Integer disciplinaId,
            Integer professorId,
            Integer salaId,
            String diaSemana,
            LocalTime inicio,
            LocalTime fim
    ) {

        validarId(disciplinaId);
        validarId(professorId);
        validarId(salaId);

        if (!adminDAO.disciplinaExiste(disciplinaId)) {
            throw new IllegalArgumentException(
                    "Disciplina não encontrada."
            );
        }

        if (!adminDAO.professorExiste(professorId)) {
            throw new IllegalArgumentException(
                    "Professor não encontrado."
            );
        }

        if (!adminDAO.salaExiste(salaId)) {
            throw new IllegalArgumentException(
                    "Sala não encontrada."
            );
        }

        if (!DIAS_VALIDOS.contains(diaSemana)) {
            throw new IllegalArgumentException(
                    "Dia da semana inválido."
            );
        }

        if (inicio == null || fim == null) {
            throw new IllegalArgumentException(
                    "Informe os horários de início e término."
            );
        }

        if (!fim.isAfter(inicio)) {
            throw new IllegalArgumentException(
                    "O horário de término deve ser posterior ao horário de início."
            );
        }

        if (adminDAO.existeConflitoProfessor(
                professorId,
                diaSemana,
                inicio,
                fim,
                id
        )) {

            throw new IllegalArgumentException(
                    "Este professor já possui uma aula neste horário."
            );
        }

        if (adminDAO.existeConflitoSala(
                salaId,
                diaSemana,
                inicio,
                fim,
                id
        )) {

            throw new IllegalArgumentException(
                    "Esta sala já está ocupada neste horário."
            );
        }

        if (id == null) {

            adminDAO.inserirAula(
                    disciplinaId,
                    professorId,
                    salaId,
                    diaSemana,
                    inicio,
                    fim
            );

            return;
        }

        if (!adminDAO.aulaExiste(id)) {
            throw new IllegalArgumentException(
                    "Aula não encontrada."
            );
        }

        adminDAO.atualizarAula(
                id,
                disciplinaId,
                professorId,
                salaId,
                diaSemana,
                inicio,
                fim
        );
    }

    public void excluirAula(Integer id) {

        validarId(id);

        if (!adminDAO.aulaExiste(id)) {
            throw new IllegalArgumentException(
                    "Aula não encontrada."
            );
        }

        adminDAO.excluirAula(id);
    }

    // =====================================================
    // VALIDAÇÕES GERAIS
    // =====================================================

    private void validarTexto(
            String valor,
            String mensagem
    ) {

        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Identificador inválido."
            );
        }
    }

}