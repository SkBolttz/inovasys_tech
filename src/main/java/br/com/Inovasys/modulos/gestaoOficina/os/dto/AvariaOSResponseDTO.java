package br.com.Inovasys.modulos.gestaoOficina.os.dto;

public record AvariaOSResponseDTO(
        Double eixoX,
        Double eixoY,
        String tipoDano,   // Ex: 'RISCO', 'AMASSADO'
        String observacao
) {
}
