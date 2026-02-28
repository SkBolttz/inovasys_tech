package br.com.Inovasys.veiculo.dto;

public record MarcaVeiculoResponseDTO(
        Long id,
        String nomeMarca,
        Boolean ativo
) {
}
