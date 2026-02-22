package br.com.Inovasys.auth.evento;

public record EnvioEmailCadastro(
        String email,
        String nome,
        String senha
) {
}
