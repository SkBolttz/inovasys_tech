package br.com.Inovasys.veiculo.mapper;

import br.com.Inovasys.veiculo.dto.CadastrarTipoVeiculoDTO;
import br.com.Inovasys.veiculo.dto.TipoVeiculoResponseDTO;
import br.com.Inovasys.veiculo.entity.TipoVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoVeiculoMapper {

    TipoVeiculo toEntity(CadastrarTipoVeiculoDTO cadastrarTipoVeiculoDTO);
    TipoVeiculoResponseDTO toResponse(TipoVeiculo tipoVeiculo);
}
