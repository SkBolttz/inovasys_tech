package br.com.Inovasys.empresa.dto.empresa;

import br.com.Inovasys.auth.dto.UserResponseDTO;
import br.com.Inovasys.empresa.dto.endereco.EnderecoResponseDTO;
import java.time.LocalDate;
import java.util.List;

public record EmpresaResponseDTO(
        Long empresa_id,
        String cnpj,
        LocalDate abertura,
        String situacao,
        String nome,
        String porte,
        String natureza_juridica,
        String atividade_principal,
        String email,
        String telefone,
        EnderecoResponseDTO endereco
) {
}
