package br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarTipoVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.TipoVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TipoVeiculoMapper {

    TipoVeiculo toEntity(CadastrarTipoVeiculoDTO cadastrarTipoVeiculoDTO);
    TipoVeiculoResponseDTO toResponse(TipoVeiculo tipoVeiculo);
}
