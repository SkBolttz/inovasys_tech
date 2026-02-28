package br.com.Inovasys.modulos.gestaoOficina.veiculo.dto;

import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa.EmpresaResponseDTO;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        MarcaVeiculoResponseDTO marca,
        ModeloVeiculoResponseDTO modelo,
        String cor,
        Integer ano,
        TipoVeiculoResponseDTO tipo,
        TipoCombustivelResponseDTO combustivel,
        ClienteResponseDTO cliente,
        EmpresaResponseDTO empresa,
        Boolean ativo
) {
}
