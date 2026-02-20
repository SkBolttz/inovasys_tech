package br.com.Inovasys.auth.dto;

import java.time.LocalDate;

public record AtualizarInformacoes(
        String nome,
        String sobrenome,
        String email,
        String telefone,
        LocalDate dataNascimento
) {
}
