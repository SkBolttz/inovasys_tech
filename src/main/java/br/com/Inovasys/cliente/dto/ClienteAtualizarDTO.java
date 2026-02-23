package br.com.Inovasys.cliente.dto;

import br.com.Inovasys.empresa.dto.endereco.EnderecoResponseDTO;

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
