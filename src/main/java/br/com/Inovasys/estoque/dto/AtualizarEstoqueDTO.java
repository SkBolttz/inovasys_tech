package br.com.Inovasys.estoque.dto;

import br.com.Inovasys.estoque.Enum.UnidadeMedida;

import java.math.BigDecimal;

public record AtualizarEstoqueDTO(
        String descricao,
        String codigo,
        BigDecimal precoCompra,
        BigDecimal precoVenda,
        Integer estoqueAtual,
        Integer estoqueMinimo,
        Integer estoqueMaximo,
        UnidadeMedida unidadeMedida
) {
}
