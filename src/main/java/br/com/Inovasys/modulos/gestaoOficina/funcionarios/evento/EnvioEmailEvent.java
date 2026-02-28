package br.com.Inovasys.modulos.gestaoOficina.funcionarios.evento;

public record EnvioEmailEvent(
        String email,
        String senha
) {
}
