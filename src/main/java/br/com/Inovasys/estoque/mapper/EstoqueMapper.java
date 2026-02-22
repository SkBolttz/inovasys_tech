package br.com.Inovasys.estoque.mapper;

import br.com.Inovasys.estoque.dto.CadastroEstoqueDTO;
import br.com.Inovasys.estoque.dto.EstoqueResponseDTO;
import br.com.Inovasys.estoque.entity.Estoque;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstoqueMapper {

    Estoque toEntity(CadastroEstoqueDTO cadastroEstoqueDTO);
    EstoqueResponseDTO toResponse(Estoque estoque);
}
