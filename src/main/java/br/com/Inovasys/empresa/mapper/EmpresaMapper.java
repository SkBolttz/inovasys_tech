package br.com.Inovasys.empresa.mapper;

import br.com.Inovasys.empresa.dto.empresa.CadastrarEmpresaDTO;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.entity.Empresa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    Empresa toEntity(CadastrarEmpresaDTO cadastrarEmpresaDTO);
    EmpresaResponseDTO toResponse(Empresa empresa);
}
