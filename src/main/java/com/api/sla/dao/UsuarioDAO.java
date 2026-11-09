package com.api.sla.dao;

import com.api.sla.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Usuario> usuarioMapper = (rs, rowNum) -> {
        Usuario usuario = new Usuario();

        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPerfil(rs.getString("perfil"));

        return usuario;
    };

    public Optional<Usuario> buscarPorEmail(String email) {

        String sql = """
                SELECT id, nome, email, senha, perfil
                FROM usuario
                WHERE email = ?
                """;

        return jdbcTemplate.query(sql, usuarioMapper, email).stream().findFirst();
    }
}
