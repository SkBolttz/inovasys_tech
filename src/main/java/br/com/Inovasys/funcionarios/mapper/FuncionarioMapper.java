package br.com.Inovasys.funcionarios.mapper;

import br.com.Inovasys.funcionarios.dto.CadastrarFuncionarioDTO;
import br.com.Inovasys.funcionarios.dto.FuncionarioResponseDTO;
import br.com.Inovasys.funcionarios.entity.Funcionario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {

    Funcionario toEntity(CadastrarFuncionarioDTO cadastrarFuncionarioDTO);
    FuncionarioResponseDTO toResponse(Funcionario funcionario);
}
