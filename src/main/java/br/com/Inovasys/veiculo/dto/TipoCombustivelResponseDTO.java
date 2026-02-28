package br.com.Inovasys.veiculo.dto;

public record TipoCombustivelResponseDTO(
        Long id,
        String tipoCombustivel,
        Boolean ativo
) {
}
