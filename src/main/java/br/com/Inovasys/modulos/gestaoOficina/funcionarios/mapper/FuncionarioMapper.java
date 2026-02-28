package br.com.Inovasys.modulos.gestaoOficina.funcionarios.mapper;

import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.CadastrarFuncionarioDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.FuncionarioResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.entity.Funcionario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    Funcionario toEntity(CadastrarFuncionarioDTO cadastrarFuncionarioDTO);
    FuncionarioResponseDTO toResponse(Funcionario funcionario);
}
