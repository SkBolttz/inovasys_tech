package br.com.Inovasys.veiculo.dto;

public record AtualizarVeiculoDTO(
        Long idCliente,
        String placa,
        Long idModelo,
        String cor,
        Integer ano,
        Long idTipoVeiculo,
        Long idTipoCombustivel
) {}
