package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import java.math.BigDecimal;

public record ItemServicoOSResponseDTO(
        String descricao,
        BigDecimal valorUnitario
) {}