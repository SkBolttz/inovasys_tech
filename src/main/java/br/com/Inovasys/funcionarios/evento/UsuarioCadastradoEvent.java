package br.com.Inovasys.funcionarios.evento;

import br.com.Inovasys.empresa.entity.Empresa;

public record UsuarioCadastradoEvent(
        String cpf,
        String email,
        Empresa empresa
) {
}
