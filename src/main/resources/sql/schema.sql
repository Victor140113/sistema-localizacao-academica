create table usuario (
    id int primary key auto_increment,
    nome varchar(255) not null,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    perfil varchar(20) not null check (perfil in ('ALUNO', 'PROFESSOR'))
);

create table disciplina (
    id int primary key auto_increment,
    nome varchar(255) not null unique
);

create table sala (
    id int primary key auto_increment,
    numero int not null,
    bloco varchar(255) not null,
    tipo varchar(20) not null check (tipo in ('TEORICA', 'PRATICA')),
    unique (bloco, numero)
);

create table aluno_disciplina (
    aluno_id int not null,
    disciplina_id int not null,
    primary key (aluno_id, disciplina_id),
    constraint fk_ad_aluno foreign key (aluno_id) references usuario (id) on delete cascade,
    constraint fk_ad_disciplina foreign key (disciplina_id) references disciplina (id) on delete cascade
);

create table aula (
    id int primary key auto_increment,
    disciplina_id int not null,
    professor_id int not null,
    sala_id int not null,
    dia_semana varchar(15) not null check (dia_semana in (
        'SEGUNDA',
        'TERCA',
        'QUARTA',
        'QUINTA',
        'SEXTA',
        'SABADO',
        'DOMINGO'
    )),
    horario_inicio time not null,
    horario_fim time not null,
    check (horario_fim > horario_inicio),
    constraint fk_aula_disciplina foreign key (disciplina_id) references disciplina (id),
    constraint fk_aula_professor foreign key (professor_id) references usuario (id),
    constraint fk_aula_sala foreign key (sala_id) references sala (id)
);