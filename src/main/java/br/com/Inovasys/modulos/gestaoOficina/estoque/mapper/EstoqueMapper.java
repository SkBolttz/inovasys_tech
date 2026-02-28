package br.com.Inovasys.modulos.gestaoOficina.estoque.mapper;

import br.com.Inovasys.modulos.gestaoOficina.estoque.dto.CadastroEstoqueDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.dto.EstoqueResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.entity.Estoque;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {

    Estoque toEntity(CadastroEstoqueDTO cadastroEstoqueDTO);
    EstoqueResponseDTO toResponse(Estoque estoque);
}
