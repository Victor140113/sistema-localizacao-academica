-- ==========================================
-- USUÁRIOS
-- Senha de demonstração: 123456
-- ==========================================

INSERT INTO usuario (nome, email, senha, perfil)
VALUES ('Lucas Almeida', 'lucas@email.com', '123456', 'ALUNO'),
       ('Mariana Costa', 'mariana@email.com', '123456', 'ALUNO'),
       ('Carlos Oliveira', 'carlos@email.com', '123456', 'PROFESSOR'),
       ('Ana Souza', 'ana@email.com', '123456', 'PROFESSOR');


-- ==========================================
-- DISCIPLINAS
-- ==========================================

INSERT INTO disciplina (nome)
VALUES ('Engenharia de Software'),
       ('Banco de Dados'),
       ('Programação Java'),
       ('Programação Web');


-- ==========================================
-- SALAS
-- ==========================================

INSERT INTO sala (numero, bloco, tipo)
VALUES (101, 'A', 'TEORICA'),
       (102, 'A', 'TEORICA'),
       (201, 'B', 'PRATICA');


-- ==========================================
-- MATRÍCULAS DOS ALUNOS
-- ==========================================

-- Lucas Almeida

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'lucas@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Engenharia de Software'));

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'lucas@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Banco de Dados'));

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'lucas@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Programação Java'));


-- Mariana Costa

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'mariana@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Engenharia de Software'));

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'mariana@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Programação Java'));

INSERT INTO aluno_disciplina (aluno_id, disciplina_id)
VALUES ((SELECT id FROM usuario WHERE email = 'mariana@email.com'),
        (SELECT id FROM disciplina WHERE nome = 'Programação Web'));


-- ==========================================
-- ALOCAÇÕES DE AULAS
-- ==========================================

-- Segunda-feira: Engenharia de Software
INSERT INTO aula (disciplina_id, professor_id, sala_id,
                      dia_semana, horario_inicio, horario_fim)
VALUES ((SELECT id FROM disciplina WHERE nome = 'Engenharia de Software'),
        (SELECT id FROM usuario WHERE email = 'carlos@email.com'),
        (SELECT id FROM sala WHERE numero = 101 AND bloco = 'A'),
        'SEGUNDA', '19:00:00', '20:40:00');


-- Terça-feira: Banco de Dados
INSERT INTO aula (disciplina_id, professor_id, sala_id,
                      dia_semana, horario_inicio, horario_fim)
VALUES ((SELECT id FROM disciplina WHERE nome = 'Banco de Dados'),
        (SELECT id FROM usuario WHERE email = 'carlos@email.com'),
        (SELECT id FROM sala WHERE numero = 201 AND bloco = 'B'),
        'TERCA', '19:00:00', '20:40:00');


-- Quarta-feira: Programação Java
INSERT INTO aula (disciplina_id, professor_id, sala_id,
                      dia_semana, horario_inicio, horario_fim)
VALUES ((SELECT id FROM disciplina WHERE nome = 'Programação Java'),
        (SELECT id FROM usuario WHERE email = 'ana@email.com'),
        (SELECT id FROM sala WHERE numero = 201 AND bloco = 'B'),
        'QUARTA', '19:00:00', '20:40:00');


-- Quinta-feira: Programação Web
INSERT INTO aula (disciplina_id, professor_id, sala_id,
                      dia_semana, horario_inicio, horario_fim)
VALUES ((SELECT id FROM disciplina WHERE nome = 'Programação Web'),
        (SELECT id FROM usuario WHERE email = 'ana@email.com'),
        (SELECT id FROM sala WHERE numero = 102 AND bloco = 'A'),
        'QUINTA', '19:00:00', '20:40:00');


-- Sexta-feira: Segunda aula de Engenharia de Software
INSERT INTO aula (disciplina_id, professor_id, sala_id,
                      dia_semana, horario_inicio, horario_fim)
VALUES ((SELECT id FROM disciplina WHERE nome = 'Engenharia de Software'),
        (SELECT id FROM usuario WHERE email = 'carlos@email.com'),
        (SELECT id FROM sala WHERE numero = 101 AND bloco = 'A'),
        'SEXTA', '19:00:00', '20:40:00');
