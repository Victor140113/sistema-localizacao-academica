package com.api.sla.controller;

import com.api.sla.dto.AulaAdminDTO;
import com.api.sla.dto.UsuarioResumoDTO;
import com.api.sla.model.Disciplina;
import com.api.sla.model.Sala;
import com.api.sla.service.AdminService;
import com.api.sla.session.SessaoUsuario;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Controller("adminController")
@RequestScope
public class AdminController {

    private final AdminService adminService;
    private final SessaoUsuario sessaoUsuario;

    // Listagens

    private List<UsuarioResumoDTO> professores = List.of();
    private List<Disciplina> disciplinas = List.of();
    private List<Sala> salas = List.of();
    private List<AulaAdminDTO> aulas = List.of();

    // Formulário de professor

    private Integer professorId;
    private String professorNome;
    private String professorEmail;
    private String professorSenha;

    // Formulário de disciplina

    private Integer disciplinaId;
    private String disciplinaNome;

    // Formulário de sala

    private Integer salaId;
    private Integer salaNumero;
    private String salaBloco;
    private String salaTipo = "TEORICA";

    // Formulário de aula

    private Integer aulaId;
    private Integer aulaDisciplinaId;
    private Integer aulaProfessorId;
    private Integer aulaSalaId;
    private String aulaDiaSemana = "SEGUNDA";
    private LocalTime aulaHorarioInicio;
    private LocalTime aulaHorarioFim;

    public AdminController(
            AdminService adminService,
            SessaoUsuario sessaoUsuario
    ) {

        this.adminService = adminService;
        this.sessaoUsuario = sessaoUsuario;
    }

    // =====================================================
    // ACESSO
    // =====================================================

    public String carregar() {

        if (!temAcesso()) {
            return "/index.xhtml?faces-redirect=true";
        }

        atualizarListagens();

        return null;
    }

    public boolean temAcesso() {

        return sessaoUsuario.isAutenticado()
                && "ADMIN".equals(sessaoUsuario.getPerfil());
    }

    public String getNomeAdmin() {
        return sessaoUsuario.getNome();
    }

    public String sair() {

        sessaoUsuario.encerrarSessao();

        return "/index.xhtml?faces-redirect=true";
    }

    // =====================================================
    // ATUALIZAÇÃO DAS LISTAGENS
    // =====================================================

    public void atualizarListagens() {

        if (!temAcesso()) {
            return;
        }

        professores = adminService.listarProfessores();
        disciplinas = adminService.listarDisciplinas();
        salas = adminService.listarSalas();
        aulas = adminService.listarAulas();
    }

    public List<UsuarioResumoDTO> getProfessores() {

        if (professores.isEmpty() && temAcesso()) {
            professores = adminService.listarProfessores();
        }

        return professores;
    }

    public List<Disciplina> getDisciplinas() {

        if (disciplinas.isEmpty() && temAcesso()) {
            disciplinas = adminService.listarDisciplinas();
        }

        return disciplinas;
    }

    public List<Sala> getSalas() {

        if (salas.isEmpty() && temAcesso()) {
            salas = adminService.listarSalas();
        }

        return salas;
    }

    public List<AulaAdminDTO> getAulas() {

        if (aulas.isEmpty() && temAcesso()) {
            aulas = adminService.listarAulas();
        }

        return aulas;
    }

    // =====================================================
    // PROFESSORES
    // =====================================================

    public void novoProfessor() {

        limparProfessor();
    }

    public void editarProfessor(UsuarioResumoDTO professor) {

        professorId = professor.getId();
        professorNome = professor.getNome();
        professorEmail = professor.getEmail();
        professorSenha = null;
    }

    public void salvarProfessor() {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.salvarProfessor(
                    professorId,
                    professorNome,
                    professorEmail,
                    professorSenha
            );

            limparProfessor();
            atualizarListagens();

        }, "Professor salvo com sucesso.");
    }

    public void excluirProfessor(Integer id) {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.excluirProfessor(id);
            atualizarListagens();

        }, "Professor excluído com sucesso.");
    }

    private void limparProfessor() {

        professorId = null;
        professorNome = null;
        professorEmail = null;
        professorSenha = null;
    }

    // =====================================================
    // DISCIPLINAS
    // =====================================================

    public void novaDisciplina() {

        limparDisciplina();
    }

    public void editarDisciplina(Disciplina disciplina) {

        disciplinaId = disciplina.getId();
        disciplinaNome = disciplina.getNome();
    }

    public void salvarDisciplina() {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.salvarDisciplina(
                    disciplinaId,
                    disciplinaNome
            );

            limparDisciplina();
            atualizarListagens();

        }, "Disciplina salva com sucesso.");
    }

    public void excluirDisciplina(Integer id) {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.excluirDisciplina(id);
            atualizarListagens();

        }, "Disciplina excluída com sucesso.");
    }

    private void limparDisciplina() {

        disciplinaId = null;
        disciplinaNome = null;
    }

    // =====================================================
    // SALAS
    // =====================================================

    public void novaSala() {

        limparSala();
    }

    public void editarSala(Sala sala) {

        salaId = sala.getId();
        salaNumero = sala.getNumero();
        salaBloco = sala.getBloco();
        salaTipo = sala.getTipo();
    }

    public void salvarSala() {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.salvarSala(
                    salaId,
                    salaNumero,
                    salaBloco,
                    salaTipo
            );

            limparSala();
            atualizarListagens();

        }, "Sala salva com sucesso.");
    }

    public void excluirSala(Integer id) {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.excluirSala(id);
            atualizarListagens();

        }, "Sala excluída com sucesso.");
    }

    private void limparSala() {

        salaId = null;
        salaNumero = null;
        salaBloco = null;
        salaTipo = "TEORICA";
    }

    // =====================================================
    // AULAS / ALOCAÇÕES
    // =====================================================

    public void novaAula() {

        limparAula();
    }

    public void editarAula(AulaAdminDTO aula) {

        aulaId = aula.getId();

        aulaDisciplinaId = aula.getDisciplinaId();
        aulaProfessorId = aula.getProfessorId();
        aulaSalaId = aula.getSalaId();

        aulaDiaSemana = aula.getDiaSemana();

        aulaHorarioInicio = aula.getHorarioInicio();
        aulaHorarioFim = aula.getHorarioFim();
    }

    public void salvarAula() {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.salvarAula(
                    aulaId,
                    aulaDisciplinaId,
                    aulaProfessorId,
                    aulaSalaId,
                    aulaDiaSemana,
                    aulaHorarioInicio,
                    aulaHorarioFim
            );

            limparAula();
            atualizarListagens();

        }, "Aula salva com sucesso.");
    }

    public void excluirAula(Integer id) {

        if (!temAcesso()) {
            return;
        }

        executar(() -> {

            adminService.excluirAula(id);
            atualizarListagens();

        }, "Aula excluída com sucesso.");
    }

    private void limparAula() {

        aulaId = null;

        aulaDisciplinaId = null;
        aulaProfessorId = null;
        aulaSalaId = null;

        aulaDiaSemana = "SEGUNDA";

        aulaHorarioInicio = null;
        aulaHorarioFim = null;
    }

    // =====================================================
    // MENSAGENS
    // =====================================================

    private void executar(
            Runnable operacao,
            String mensagemSucesso
    ) {

        try {

            if (!temAcesso()) {
                return;
            }

            operacao.run();

            adicionarMensagem(
                    FacesMessage.SEVERITY_INFO,
                    mensagemSucesso
            );

        } catch (IllegalArgumentException e) {

            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    e.getMessage()
            );

        } catch (Exception e) {

            adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Não foi possível concluir a operação."
            );
        }
    }

    private void adicionarMensagem(
            FacesMessage.Severity severidade,
            String mensagem
    ) {

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(severidade, mensagem, null)
        );
    }

}