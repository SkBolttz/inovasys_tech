package br.com.Inovasys.modulos.gestaoOficina.veiculo.dto;

public record AtualizarVeiculoDTO(
        Long idCliente,
        String placa,
        Long idModelo,
        String cor,
        Integer ano,
        Long idTipoVeiculo,
        Long idTipoCombustivel
) {}
