package br.com.Inovasys.infra.security;

import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ObterUsuarioLogado {
    public static String obterCpfUsuarioLogado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsuarioNaoLocalizadoException("Usuário não autenticado.");
        }

        System.out.println("Authentication: " + authentication.getName());
        return authentication.getName();
    }
}
