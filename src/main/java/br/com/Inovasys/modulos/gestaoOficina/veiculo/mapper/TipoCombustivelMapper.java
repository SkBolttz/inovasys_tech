package br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarTipoCombustivelDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.TipoCombustivelResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoCombustivel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoCombustivelMapper {

    TipoCombustivel toEntity(CadastrarTipoCombustivelDTO cadastrarTipoCombustivelDTO);
    TipoCombustivelResponseDTO toResponse(TipoCombustivel tipoCombustivel);
}
