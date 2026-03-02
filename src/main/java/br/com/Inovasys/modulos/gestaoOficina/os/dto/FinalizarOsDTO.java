package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import br.com.Inovasys.modulos.gestaoOficina.os.enuns.FormaPagamento;
import java.math.BigDecimal;

public record FinalizarOsDTO(
        Long osId,
        Integer quilometragemSaida,
        String diagnostico,
        FormaPagamento formaPagamento,
        Integer parcelas,
        Integer garantiaDias,
        BigDecimal desconto
) {
}
