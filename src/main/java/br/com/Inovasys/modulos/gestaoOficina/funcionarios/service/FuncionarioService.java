package br.com.Inovasys.modulos.gestaoOficina.funcionarios.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.*;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.infra.security.ObterUsuarioLogado;
import br.com.Inovasys.infra.exceptions.FuncionarioException.FuncionarioNaoLocalizadoException;
import br.com.Inovasys.infra.exceptions.FuncionarioException.FuncionarioStatusException;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.AtualizarFuncionarioDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.CadastrarFuncionarioDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.FuncionarioResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.entity.Funcionario;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.evento.UsuarioCadastradoEvent;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.mapper.FuncionarioMapper;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.repository.FuncionarioRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final UsersRepository usersRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            UsersRepository usersRepository,
            FuncionarioMapper funcionarioMapper,
            ApplicationEventPublisher applicationEventPublisher) {

        this.funcionarioRepository = funcionarioRepository;
        this.usersRepository = usersRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /* =====================================================
       CADASTRAR
       ===================================================== */

    public FuncionarioResponseDTO cadastrarFuncionario(CadastrarFuncionarioDTO dto) {

        Users usuarioLogado = localizarUsuario();
        Empresa empresa = usuarioLogado.getEmpresa();

        String cpfNormalizado = normalizarCpf(dto.cpf());

        validarDuplicidadeCadastro(
                empresa,
                dto.email(),
                cpfNormalizado,
                dto.telefone()
        );

        Funcionario funcionario = funcionarioMapper.toEntity(dto);

        funcionario.setCpf(cpfNormalizado);
        funcionario.setDataAdmissao(LocalDate.now());
        funcionario.setAtivo(true);
        funcionario.setEmpresa(empresa);

        funcionarioRepository.save(funcionario);

        applicationEventPublisher.publishEvent(
                new UsuarioCadastradoEvent(
                        cpfNormalizado,
                        funcionario.getEmail(),
                        empresa
                )
        );

        return funcionarioMapper.toResponse(funcionario);
    }

    /* =====================================================
       ATUALIZAR
       ===================================================== */

    public FuncionarioResponseDTO atualizarFuncionario(
            AtualizarFuncionarioDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Funcionario funcionario =
                localizarFuncionarioPorCpf(normalizarCpf(dto.cpf()), empresa);

        validarDuplicidadeUpdate(
                funcionario.getId(),
                empresa,
                dto.email(),
                dto.telefone()
        );

        if (dto.nome() != null)
            funcionario.setNome(dto.nome());

        if (dto.email() != null)
            funcionario.setEmail(dto.email());

        if (dto.telefone() != null)
            funcionario.setTelefone(dto.telefone());

        funcionarioRepository.save(funcionario);

        return funcionarioMapper.toResponse(funcionario);
    }

    /* =====================================================
       ATIVAR
       ===================================================== */

    public FuncionarioResponseDTO ativarFuncionario(String cpf) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Funcionario funcionario =
                localizarFuncionarioPorCpf(normalizarCpf(cpf), empresa);

        if (funcionario.getAtivo()) {
            throw new FuncionarioStatusException(
                    "Funcionário já está ativo"
            );
        }

        funcionario.setAtivo(true);
        funcionarioRepository.save(funcionario);

        return funcionarioMapper.toResponse(funcionario);
    }

    /* =====================================================
       INATIVAR
       ===================================================== */

    public FuncionarioResponseDTO inativarFuncionario(String cpf) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Funcionario funcionario =
                localizarFuncionarioPorCpf(normalizarCpf(cpf), empresa);

        if (!funcionario.getAtivo()) {
            throw new FuncionarioStatusException(
                    "Funcionário já está inativo"
            );
        }

        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);

        return funcionarioMapper.toResponse(funcionario);
    }

    /* =====================================================
       BUSCAR POR CPF
       ===================================================== */

    public FuncionarioResponseDTO buscarFuncionarioPorCPF(String cpf) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Funcionario funcionario =
                localizarFuncionarioPorCpf(normalizarCpf(cpf), empresa);

        return funcionarioMapper.toResponse(funcionario);
    }

    /* =====================================================
       BUSCAR POR NOME
       ===================================================== */

    public Page<FuncionarioResponseDTO> buscarFuncionarioPorNome(
            String nome,
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Page<Funcionario> funcionarios =
                funcionarioRepository
                        .findByNomeContainingIgnoreCaseAndEmpresa(
                                nome,
                                empresa,
                                pageable
                        );

        if (funcionarios.isEmpty()) {
            throw new FuncionarioNaoLocalizadoException(
                    "Nenhum funcionário encontrado"
            );
        }

        return funcionarios.map(funcionarioMapper::toResponse);
    }

    /* =====================================================
       LISTAR TODOS
       ===================================================== */

    public Page<FuncionarioResponseDTO> buscarTodosFuncionarios(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return funcionarioRepository
                .findByEmpresa(empresa, pageable)
                .map(funcionarioMapper::toResponse);
    }

    public Page<FuncionarioResponseDTO> buscarTodosFuncionariosAtivos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return funcionarioRepository
                .findByAtivoAndEmpresa(true, empresa, pageable)
                .map(funcionarioMapper::toResponse);
    }

    public Page<FuncionarioResponseDTO> buscarTodosFuncionariosInativos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return funcionarioRepository
                .findByAtivoAndEmpresa(false, empresa, pageable)
                .map(funcionarioMapper::toResponse);
    }

    /* =====================================================
       MÉTODOS PRIVADOS
       ===================================================== */

    private Empresa obterEmpresaDoUsuarioLogado() {
        return localizarUsuario().getEmpresa();
    }

    private Funcionario localizarFuncionarioPorCpf(
            String cpf,
            Empresa empresa) {

        return funcionarioRepository
                .findByCpfAndEmpresa(cpf, empresa)
                .orElseThrow(() ->
                        new FuncionarioNaoLocalizadoException(
                                "Funcionário não localizado."
                        ));
    }

    private Users localizarUsuario() {

        return usersRepository
                .findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado())
                .orElseThrow(() ->
                        new UsuarioNaoLocalizadoException(
                                "Usuário não localizado."
                        ));
    }

    private String normalizarCpf(String cpf) {

        if (cpf == null)
            throw new CPFInvalidoException("CPF não pode ser nulo");

        String cpfNormalizado = cpf.replaceAll("\\D", "");

        if (!cpfNormalizado.matches("\\d{11}"))
            throw new CPFInvalidoException("CPF inválido");

        return cpfNormalizado;
    }

    private void validarDuplicidadeCadastro(
            Empresa empresa,
            String email,
            String cpf,
            String telefone) {

        if (funcionarioRepository
                .existsByEmailAndEmpresa(email, empresa))
            throw new EmailDuplicadoException("E-mail já cadastrado");

        if (funcionarioRepository
                .existsByCpfAndEmpresa(cpf, empresa))
            throw new CPFDuplicadoException("CPF já cadastrado");

        if (funcionarioRepository
                .existsByTelefoneAndEmpresa(telefone, empresa))
            throw new TelefoneDuplicadoException("Telefone já cadastrado");
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String email,
            String telefone) {

        if (email != null &&
                funcionarioRepository
                        .existsByEmailAndEmpresaAndIdNot(
                                email, empresa, id))
            throw new EmailDuplicadoException("E-mail já cadastrado");

        if (telefone != null &&
                funcionarioRepository
                        .existsByTelefoneAndEmpresaAndIdNot(
                                telefone, empresa, id))
            throw new TelefoneDuplicadoException("Telefone já cadastrado");
    }
}
