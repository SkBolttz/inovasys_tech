package br.com.Inovasys.cliente.mapper;

import br.com.Inovasys.cliente.dto.ClienteCadastroDTO;
import br.com.Inovasys.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.cliente.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteCadastroDTO clienteCadastroDTO);
    ClienteResponseDTO toResponse(Cliente cliente);
}
