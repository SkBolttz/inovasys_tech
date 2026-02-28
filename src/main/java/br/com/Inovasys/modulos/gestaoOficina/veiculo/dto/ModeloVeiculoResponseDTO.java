package br.com.Inovasys.modulos.gestaoOficina.veiculo.dto;

public record ModeloVeiculoResponseDTO(
        Long id,
        String modeloVeiculo,
        MarcaVeiculoResponseDTO marcaVeiculo,
        Boolean ativo
) {
}
