package br.com.Inovasys.veiculo.dto;

import br.com.Inovasys.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;

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
