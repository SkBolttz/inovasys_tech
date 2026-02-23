package br.com.Inovasys.cliente.dto;

import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.dto.endereco.EnderecoResponseDTO;
import java.time.LocalDate;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpfCnpj,
        String email,
        String telefone,
        LocalDate dataNascimento,
        EnderecoResponseDTO endereco,
        LocalDate dataCadastro,
        Boolean ativo,
        EmpresaResponseDTO empresa
) {
}
