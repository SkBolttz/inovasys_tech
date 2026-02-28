package br.com.Inovasys.modulos.gestaoOficina.cliente.dto;

import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.endereco.EnderecoResponseDTO;

import java.time.LocalDate;

public record ClienteCadastroDTO(
        String nome,
        String cpfCnpj,
        String email,
        String telefone,
        LocalDate dataNascimento,
        EnderecoResponseDTO endereco,
        LocalDate dataCadastro
) {
}
