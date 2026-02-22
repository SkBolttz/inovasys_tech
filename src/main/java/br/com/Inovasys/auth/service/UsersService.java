package br.com.Inovasys.auth.service;

import br.com.Inovasys.auth.dto.AtualizarInformacoes;
import br.com.Inovasys.auth.dto.NovaSenhaDTO;
import br.com.Inovasys.auth.dto.UserResponseDTO;
import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.*;
import br.com.Inovasys.auth.mapper.UsersMapper;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.role.PerfilUsuario;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.funcionarios.evento.EnvioEmailEvent;
import br.com.Inovasys.funcionarios.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;
    private final FuncionarioRepository funcionarioRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, UsersMapper usersMapper,
                        FuncionarioRepository funcionarioRepository, ApplicationEventPublisher applicationEventPublisher ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersMapper = usersMapper;
        this.funcionarioRepository = funcionarioRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public UserResponseDTO cadastrarUsuario(String cpf, String email, Empresa empresa) {

        Boolean existe = usersRepository.existsByCpf(cpf).orElseThrow( () -> new CPFInvalidoException("Erro ao verificar CPF."));
        if(existe){
            throw new CPFDuplicadoException("CPF já cadastrado em sistema.");
        }

        Users user = new Users();
        user.setCpf(cpf);
        String senhaGerada = gerarSenhaAleatoria();
        user.setSenhaHash(passwordEncoder.encode(senhaGerada));

        if(validarFuncionario(cpf)){
            user.setPerfilUsuario(PerfilUsuario.FUNCIONARIO);
            user.setEmpresa(empresa);
        }else{
            user.setPerfilUsuario(PerfilUsuario.DONO);
        }

        usersRepository.save(user);

        applicationEventPublisher.publishEvent(
                new EnvioEmailEvent(email, senhaGerada)
        );

        return usersMapper.toResponse(user);
    }

    public String atualizarSenhaPrimeiroAcesso(NovaSenhaDTO novaSenha) {

        Users usuario = usersRepository.findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado())
                .orElseThrow(() -> new UsuarioNaoLocalizadoException("Usuário não encontrado"));

        if (!usuario.getPrimeiroLogin()) {
            throw new ErroSenhaException("A senha já foi atualizada anteriormente");
        }

        if (novaSenha.novaSenha() == null || novaSenha.novaSenha().isBlank()) {
            throw new ErroSenhaException("Senha inválida");
        }

        if(novaSenha.novaSenha().equals(usuario.getSenhaHash())){
            throw new ErroSenhaException("A nova senha deve ser diferente da senha atual");
        }

        usuario.setSenhaHash(passwordEncoder.encode(novaSenha.novaSenha()));

        usersRepository.save(usuario);

        return "Senha atualizada com sucesso";
    }


    private String gerarSenhaAleatoria() {
        int comprimento = 8;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < comprimento; i++) {
            int indice = (int) (Math.random() * caracteres.length());
            senha.append(caracteres.charAt(indice));
        }

        return senha.toString();
    }

    public UserResponseDTO atualizarInformacoes(@Valid AtualizarInformacoes atualizarInformacoes) {

        validarDuplicidades(atualizarInformacoes.email(), atualizarInformacoes.telefone());

        Users user = localizarUser();

        user.setNome(atualizarInformacoes.nome());
        user.setSobrenome(atualizarInformacoes.sobrenome());
        user.setEmail(atualizarInformacoes.email());
        user.setTelefone(atualizarInformacoes.telefone());
        user.setDataNascimento(atualizarInformacoes.dataNascimento());

        //Faz com que a tela de alteração inicial pare de ser apresentada
        //phhborba 20/02/2026
        user.setPrimeiroLogin(false);

        usersRepository.save(user);
        return usersMapper.toResponse(user);
    }

    public UserResponseDTO buscarUsuario(){
        return usersMapper.toResponse(localizarUser());
    }

    private void validarDuplicidades(String email, String telefone){
        if(usersRepository.existsByEmail(email)){
            throw new EmailDuplicadoException("Email já cadastrado em sistema.");
        }
        if(usersRepository.existsByTelefone(telefone)){
            throw new TelefoneDuplicadoException("Telefone já cadastrado em sistema.");
        }
    }

    private Users localizarUser(){
        return usersRepository.findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado()).orElseThrow(
                () -> new UsuarioNaoLocalizadoException("Usuário não localizado."));
    }

    private Boolean validarFuncionario(String cpf){
        return funcionarioRepository.existsByCpf(cpf).isPresent();
    }
}
