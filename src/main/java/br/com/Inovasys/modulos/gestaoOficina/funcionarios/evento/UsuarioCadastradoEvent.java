package br.com.Inovasys.modulos.gestaoOficina.funcionarios.evento;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;

public record UsuarioCadastradoEvent(
        String cpf,
        String email,
        Empresa empresa
) {
}
