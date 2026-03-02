package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import java.math.BigDecimal;

public record AdicionarProdutoOSDTO(
        Long osId,
        Long idProduto,
        Integer quantidade,
        BigDecimal valorAplicado
) {
}
