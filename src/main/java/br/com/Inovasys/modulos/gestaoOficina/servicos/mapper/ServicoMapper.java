package br.com.Inovasys.modulos.gestaoOficina.servicos.mapper;

import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.AtualizarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.CadastrarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.ServicoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.entity.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    Servico toEntity(CadastrarServicoDTO dto);

    ServicoResponseDTO toDto(Servico servico);
}
