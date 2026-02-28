package br.com.Inovasys.auth.evento;

import br.com.Inovasys.auth.service.EmailService;
import br.com.Inovasys.auth.service.UsersService;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.evento.EnvioEmailEvent;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.evento.UsuarioCadastradoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioListener {

    private final UsersService usersService;
    private final EmailService emailService;

    @EventListener
    public void aoCadastrarUsuario(UsuarioCadastradoEvent event) {
        usersService.cadastrarUsuario(event.cpf(), event.email(), event.empresa());
    }

    @EventListener
    public void aoCadastrarEnviarEmail(EnvioEmailEvent event){
        emailService.enviarEmailBoasVindas(event.email(), event.senha());
    }

}
