package br.com.Inovasys.modulos.gestaoOficina.servicos.dto;

import java.math.BigDecimal;

public record ServicoResponseDTO(
        Long id,
        String descricao,
        String observacao,
        BigDecimal valorMaoDeObra,
        String tempoEstimado,
        Boolean ativo
) {
}
