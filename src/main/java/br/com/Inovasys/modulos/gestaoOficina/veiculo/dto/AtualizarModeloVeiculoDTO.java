package br.com.Inovasys.modulos.gestaoOficina.veiculo.dto;

public record AtualizarModeloVeiculoDTO(
        Long idModelo,
        String modeloVeiculo,
        Long idMarca
) {
}
