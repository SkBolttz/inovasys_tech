package br.com.Inovasys.modulos.gestaoOficina.cliente.dto;

import java.time.LocalDate;

public record ClienteAtualizarDTO(
        Long idCliente,
        String nome,
        String cpfCnpj,
        String email,
        String telefone,
        LocalDate dataNascimento
) {
}
