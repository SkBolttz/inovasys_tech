package br.com.Inovasys.funcionarios.evento;

public record EnvioEmailEvent(
        String email,
        String senha
) {
}
