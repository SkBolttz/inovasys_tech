package br.com.Inovasys.modulos.gestaoOficina.itemEstoqueOs.mapper;

import br.com.Inovasys.modulos.gestaoOficina.itemEstoqueOs.entity.ItemEstoqueOS;
import br.com.Inovasys.modulos.gestaoOficina.os.dto.ItemEstoqueOSResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemEstoqueOSMapper {

    @Mapping(source = "produto.descricao", target = "descricao")
    @Mapping(source = "valorUnitario", target = "valorUnitario")
    @Mapping(source = "quantidade", target = "quantidade")
    @Mapping(source = "valorTotal", target = "valorTotal")
    ItemEstoqueOSResponseDTO toDTO(ItemEstoqueOS estoque);
}