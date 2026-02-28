package br.com.Inovasys.veiculo.mapper;

import br.com.Inovasys.veiculo.dto.CadastrarModeloVeiculoDTO;
import br.com.Inovasys.veiculo.dto.ModeloVeiculoResponseDTO;
import br.com.Inovasys.veiculo.entity.ModeloVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloVeiculoMapper {

    ModeloVeiculo toEntity(CadastrarModeloVeiculoDTO cadastrarModeloVeiculoDTO);
    ModeloVeiculoResponseDTO toResponse(ModeloVeiculo modeloVeiculo);
}
