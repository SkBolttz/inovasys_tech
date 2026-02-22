package br.com.Inovasys.funcionarios.dto;

public record AtualizarFuncionarioDTO(
        String cpf,
        String nome,
        String email,
        String telefone
) {
}
