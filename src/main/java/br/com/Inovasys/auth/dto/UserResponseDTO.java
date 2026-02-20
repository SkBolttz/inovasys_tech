package br.com.Inovasys.auth.dto;

import br.com.Inovasys.auth.role.PerfilUsuario;
import br.com.Inovasys.auth.role.StatusUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        Long empresaId,

        String nome,
        String sobrenome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento,

        StatusUsuario status,
        PerfilUsuario perfilUsuario,

        Integer tentativasLogin,
        LocalDateTime ultimoLogin,
        Boolean primeiroLogin,

        LocalDateTime dataCadastro
) {
}
