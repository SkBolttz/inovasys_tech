package br.com.Inovasys.veiculo.mapper;

import br.com.Inovasys.veiculo.dto.CadastrarMarcaVeiculoDTO;
import br.com.Inovasys.veiculo.dto.MarcaVeiculoResponseDTO;
import br.com.Inovasys.veiculo.entity.MarcaVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaVeiculoMapper {

    MarcaVeiculo toEntity(CadastrarMarcaVeiculoDTO cadastrarMarcaVeiculoDTO);
    MarcaVeiculoResponseDTO toResponse(MarcaVeiculo marcaVeiculo);
}
