package br.com.Inovasys.auth.mapper;

import br.com.Inovasys.auth.dto.AtualizarInformacoes;
import br.com.Inovasys.auth.dto.UserResponseDTO;
import br.com.Inovasys.auth.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    Users toEntity(AtualizarInformacoes atualizarInformacoes);
    UserResponseDTO toResponse(Users users);
}
