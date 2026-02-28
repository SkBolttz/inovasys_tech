package br.com.Inovasys.modulos.gestaoOficina.servicos.dto;

import java.math.BigDecimal;

public record CadastrarServicoDTO(
        String descricao,
        String observacao,
        BigDecimal valorMaoDeObra,
        String tempoEstimado){
}
