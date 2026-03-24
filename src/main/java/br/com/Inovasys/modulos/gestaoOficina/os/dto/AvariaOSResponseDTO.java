package br.com.Inovasys.modulos.gestaoOficina.os.dto;

public record AvariaOSResponseDTO(
        Double eixoX,
        Double eixoY,

        String parteVeiculo,
        String tipoDano,
        String observacao
) {
}
