package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import java.math.BigDecimal;

public record ItemEstoqueOSResponseDTO(
        Long id,
        String descricao,
        BigDecimal valorUnitario,
        Integer quantidade,
        BigDecimal valorTotal
) {}