package br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarModeloVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.ModeloVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.ModeloVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModeloVeiculoMapper {

    ModeloVeiculo toEntity(CadastrarModeloVeiculoDTO cadastrarModeloVeiculoDTO);
    ModeloVeiculoResponseDTO toResponse(ModeloVeiculo modeloVeiculo);
}
