package br.com.Inovasys.modulos.gestaoOficina.estoque.dto;

import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.Enum.UnidadeMedida;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EstoqueResponseDTO(
        Long id,
        String descricao,
        String codigo,
        BigDecimal precoCompra,
        BigDecimal precoVenda,
        Integer estoqueAtual,
        Integer estoqueMinimo,
        Integer estoqueMaximo,
        UnidadeMedida unidadeMedida,
        LocalDate ultimaReposicao,
        EmpresaResponseDTO empresa,
        Boolean ativo
) {
}
