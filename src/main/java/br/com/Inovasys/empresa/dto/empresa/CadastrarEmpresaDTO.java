package br.com.Inovasys.empresa.dto.empresa;

import br.com.Inovasys.empresa.dto.endereco.EnderecoResponseDTO;
import java.time.LocalDate;

public record CadastrarEmpresaDTO(
        String cnpj,
        String dataAbertura,
        String situacao,
        String nome,
        String porte,
        String naturezaJuridica,
        String atividadePrincipal,
        String email,
        String telefone,
        EnderecoResponseDTO endereco
) {
}
