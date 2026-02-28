package br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa;

import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.endereco.EnderecoResponseDTO;
import java.time.LocalDate;

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
