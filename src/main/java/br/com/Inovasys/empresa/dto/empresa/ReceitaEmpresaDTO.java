package br.com.Inovasys.empresa.dto.empresa;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ReceitaEmpresaDTO(
        String abertura,
        String situacao,
        String nome,
        String porte,
        @JsonProperty("natureza_juridica")
        String naturezaJuridica,
        @JsonProperty("atividade_principal")
        List<AtividadeDTO> atividadePrincipal,
        String logradouro,
        String numero,
        String municipio,
        String bairro,
        String uf,
        String cep,
        String email,
        String telefone
) {
}
