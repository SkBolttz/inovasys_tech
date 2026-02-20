package br.com.Inovasys.empresa.dto.endereco;

public record EnderecoResponseDTO(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String municipio,
        String uf,
        String cep
) {
}
