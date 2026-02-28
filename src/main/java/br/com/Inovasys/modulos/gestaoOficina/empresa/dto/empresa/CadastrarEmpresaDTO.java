package br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa;

import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.endereco.EnderecoResponseDTO;

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
