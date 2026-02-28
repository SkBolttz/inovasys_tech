package br.com.Inovasys.veiculo.mapper;

import br.com.Inovasys.veiculo.dto.CadastrarVeiculoDTO;
import br.com.Inovasys.veiculo.dto.VeiculoResponseDTO;
import br.com.Inovasys.veiculo.entity.Veiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    Veiculo toEntity(CadastrarVeiculoDTO cadastrarVeiculoDTO);
    VeiculoResponseDTO toResponse(Veiculo veiculo);
}
