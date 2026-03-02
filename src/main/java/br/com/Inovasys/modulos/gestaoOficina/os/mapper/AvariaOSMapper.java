package br.com.Inovasys.modulos.gestaoOficina.os.mapper;

import br.com.Inovasys.modulos.gestaoOficina.os.dto.AvariaOSResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.os.entity.AvariaOS;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AvariaOSMapper {

    AvariaOS toEntity(AvariaOSResponseDTO avariaDto);
    AvariaOSResponseDTO toResponse(AvariaOS avariaOS);

}
