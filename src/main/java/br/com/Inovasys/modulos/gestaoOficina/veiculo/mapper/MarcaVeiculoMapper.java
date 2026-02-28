package br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarMarcaVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.MarcaVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.MarcaVeiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaVeiculoMapper {

    MarcaVeiculo toEntity(CadastrarMarcaVeiculoDTO cadastrarMarcaVeiculoDTO);
    MarcaVeiculoResponseDTO toResponse(MarcaVeiculo marcaVeiculo);
}
