package br.com.Inovasys.estoque.dto;

import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.estoque.Enum.UnidadeMedida;
import jakarta.persistence.*;

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
