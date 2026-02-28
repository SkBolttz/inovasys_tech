package br.com.Inovasys.veiculo.mapper;

import br.com.Inovasys.veiculo.dto.CadastrarTipoCombustivelDTO;
import br.com.Inovasys.veiculo.dto.TipoCombustivelResponseDTO;
import br.com.Inovasys.veiculo.entity.TipoCombustivel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoCombustivelMapper {

    TipoCombustivel toEntity(CadastrarTipoCombustivelDTO cadastrarTipoCombustivelDTO);
    TipoCombustivelResponseDTO toResponse(TipoCombustivel tipoCombustivel);
}
