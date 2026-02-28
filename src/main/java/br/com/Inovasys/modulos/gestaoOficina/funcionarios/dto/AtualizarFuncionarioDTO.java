package br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto;

public record AtualizarFuncionarioDTO(
        Long idFuncionario,
        String cpf,
        String nome,
        String email,
        String telefone
) {
}
