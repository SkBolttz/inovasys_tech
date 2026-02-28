package br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto;

import java.time.LocalDate;

public record CadastrarFuncionarioDTO(
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDate dataNascimento
) {
}
