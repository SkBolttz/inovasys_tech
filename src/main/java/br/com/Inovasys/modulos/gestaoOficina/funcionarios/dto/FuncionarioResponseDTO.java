package br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto;

import java.time.LocalDate;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento,
        LocalDate dataAdmissao,
        Boolean ativo
) {
}
