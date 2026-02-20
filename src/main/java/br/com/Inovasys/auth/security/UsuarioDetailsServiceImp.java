package br.com.Inovasys.auth.security;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImp implements UserDetailsService {

    private final UsersRepository usuarioRepository;

    public UsuarioDetailsServiceImp(UsersRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Users user = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com CNPJ: " + cpf));
        return user;
    }
}