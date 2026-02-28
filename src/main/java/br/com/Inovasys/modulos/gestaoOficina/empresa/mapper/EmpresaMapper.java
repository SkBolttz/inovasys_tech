package br.com.Inovasys.modulos.gestaoOficina.empresa.mapper;

import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa.CadastrarEmpresaDTO;
import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    Empresa toEntity(CadastrarEmpresaDTO cadastrarEmpresaDTO);
    EmpresaResponseDTO toResponse(Empresa empresa);
}
