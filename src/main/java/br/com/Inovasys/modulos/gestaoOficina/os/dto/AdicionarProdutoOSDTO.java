package br.com.Inovasys.modulos.gestaoOficina.os.dto;

public record AdicionarProdutoOSDTO(
        Long osId,
        Long idProduto,
        Integer quantidade
) {
}
