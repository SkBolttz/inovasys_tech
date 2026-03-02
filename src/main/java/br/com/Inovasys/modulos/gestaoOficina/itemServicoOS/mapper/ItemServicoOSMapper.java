package br.com.Inovasys.modulos.gestaoOficina.itemServicoOS.mapper;

import br.com.Inovasys.modulos.gestaoOficina.itemServicoOS.entity.ItemServicoOS;
import br.com.Inovasys.modulos.gestaoOficina.os.dto.ItemServicoOSResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemServicoOSMapper {

    @Mapping(source = "servico.descricao", target = "descricao")
    ItemServicoOSResponseDTO toDTO(ItemServicoOS item);
}
