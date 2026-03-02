package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import java.math.BigDecimal;

public record AdicionarServicoOSDTO(
        Long osId,
        Long idServico,
        BigDecimal valorAplicado,
        Integer quantidade,
        Long idFuncionarioExecutor
) {
}