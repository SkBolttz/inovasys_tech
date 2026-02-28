package br.com.Inovasys.veiculo.dto;

public record AtualizarModeloVeiculoDTO(
        Long idModelo,
        String modeloVeiculo,
        Long idMarca
) {
}
