package br.com.Inovasys.veiculo.dto;

public record ModeloVeiculoResponseDTO(
        Long id,
        String modeloVeiculo,
        MarcaVeiculoResponseDTO marcaVeiculo,
        Boolean ativo
) {
}
