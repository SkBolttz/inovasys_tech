package br.com.Inovasys.modulos.gestaoOficina.cliente.mapper;

import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteCadastroDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteCadastroDTO clienteCadastroDTO);
    ClienteResponseDTO toResponse(Cliente cliente);
}
