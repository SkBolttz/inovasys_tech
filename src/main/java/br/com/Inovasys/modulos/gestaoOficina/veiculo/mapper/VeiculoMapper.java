package br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.VeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    Veiculo toEntity(CadastrarVeiculoDTO cadastrarVeiculoDTO);
    VeiculoResponseDTO toResponse(Veiculo veiculo);
}
