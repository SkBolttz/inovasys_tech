package br.com.Inovasys.modulos.gestaoOficina.os.dto;

public record AtualizarQuantidadeProdutoOS(
        Long idOs,
        Long idProduto,
        Integer quantidade
) {
}
