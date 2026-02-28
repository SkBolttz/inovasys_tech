package br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto;

public record AtualizarFuncionarioDTO(
        String cpf,
        String nome,
        String email,
        String telefone
) {
}
