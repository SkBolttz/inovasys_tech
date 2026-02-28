package br.com.Inovasys.modulos.gestaoOficina.estoque.dto;

import br.com.Inovasys.modulos.gestaoOficina.estoque.Enum.UnidadeMedida;

import java.math.BigDecimal;

public record AtualizarEstoqueDTO(
        Long idItem,
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
