package br.com.Inovasys.modulos.gestaoOficina.veiculo.dto;

public record CadastrarVeiculoDTO(
        String placa,
        Long idMarca,
        Long idModelo,
        String cor,
        Integer ano,
        Long idTipoVeiculo,
        Long idTipoCombustivel,
        Long idCliente,
        Long idEmpresa
) {}
