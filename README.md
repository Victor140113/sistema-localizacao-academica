
# SLA — Sistema de Localização Acadêmica

Sistema web desenvolvido para auxiliar na consulta e no gerenciamento de informações acadêmicas, como disciplinas, salas e aulas.

## Aplicação online

**[Acessar o SLA](https://sla-app.onrender.com/index.xhtml)**

## Sobre o projeto

O SLA é uma aplicação web desenvolvida como projeto acadêmico, utilizando Java e Spring Boot.

O sistema utiliza PostgreSQL para persistência dos dados e JdbcTemplate para executar as operações de acesso ao banco de dados.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- JSF (JavaServer Faces)
- JoinFaces
- JdbcTemplate
- PostgreSQL
- Maven

## Banco de dados

O banco de dados possui as seguintes tabelas principais:

- `usuario`
- `disciplina`
- `sala`
- `aluno_disciplina`
- `aula`

## Acesso para demonstração

As credenciais de demonstração serão disponibilizadas abaixo para permitir a avaliação da aplicação.

| Perfil | E-mail | Senha |
|---|---|---|
| Professor | carlos@email.com | 123456 |
| Aluno | lucas@email.com | 123456 |
| Admin | admin@sla.com | 123456 |

## Execução local

Para executar o projeto localmente, é necessário ter Java 21, Maven e PostgreSQL instalados.

1. Clone o repositório:

   ```bash
   git clone https://github.com/Victor140113/sistema-localizacao-academica
   ```

2. Configure as variáveis de ambiente necessárias para conexão com o PostgreSQL:
   - `DB_URL_SLA`
   - `DB_USERNAME_POSTGRES`
   - `DB_PASSWORD`

3. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

4. Acesse:

   ```text
   http://localhost:8080/index.xhtml
   ```

---

**Projeto acadêmico — SLA (Sistema de Localização Acadêmica).**
