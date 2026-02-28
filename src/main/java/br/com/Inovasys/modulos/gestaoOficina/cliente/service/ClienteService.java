package br.com.Inovasys.modulos.gestaoOficina.cliente.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.infra.exceptions.ClienteException.ClienteNaoLocalizadoException;
import br.com.Inovasys.infra.exceptions.ClienteException.DuplicidadeCnpjCpfException;
import br.com.Inovasys.infra.exceptions.ClienteException.DuplicidadeEmailClienteException;
import br.com.Inovasys.infra.exceptions.ClienteException.DuplicidadeTelefoneClienteException;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteAtualizarDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteCadastroDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.entity.Cliente;
import br.com.Inovasys.modulos.gestaoOficina.cliente.mapper.ClienteMapper;
import br.com.Inovasys.modulos.gestaoOficina.cliente.repository.ClienteRepository;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final UsersRepository usersRepository;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, UsersRepository usersRepository){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.usersRepository = usersRepository;
    }

    public ClienteResponseDTO cadastrarCliente(@Valid ClienteCadastroDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        validarDuplicidadeCadastro(empresa, dto.cpfCnpj(), dto.email());

        Cliente cliente = clienteMapper.toEntity(dto);

        cliente.setDataCadastro(LocalDate.now());
        cliente.setEmpresa(empresa);
        cliente.setAtivo(true);

        clienteRepository.save(cliente);

        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO editarCliente(@Valid ClienteAtualizarDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Cliente cliente = localizarClienteCpfCnpj(empresa, dto.cpfCnpj());
        validarDuplicidadeUpdate(cliente.getId(), empresa, dto.email(), dto.telefone());

        if (dto.nome() != null) {
            cliente.setNome(dto.nome());
        }
        if (dto.email() != null) {
            cliente.setEmail(dto.email());
        }
        if (dto.telefone() != null) {
            cliente.setTelefone(dto.telefone());
        }
        if(dto.dataNascimento() != null){
            cliente.setDataNascimento(dto.dataNascimento());
        }

        clienteRepository.save(cliente);

        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO ativarCliente(String cnpjCpf) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Cliente cliente = localizarClienteCpfCnpj(empresa, cnpjCpf);
        cliente.setAtivo(true);
        clienteRepository.save(cliente);
        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO desativarCliente(String cnpjCpf) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Cliente cliente = localizarClienteCpfCnpj(empresa, cnpjCpf);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
        return clienteMapper.toResponse(cliente);
    }

    public ClienteResponseDTO buscarClientePorCpfCnpj(String cpfCnpj) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return clienteMapper.toResponse(localizarClienteCpfCnpj(empresa, cpfCnpj));
    }

    public Page<ClienteResponseDTO> buscarClientePorNome(String nome, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Page<Cliente> clientes = clienteRepository
                .findByNomeContainingIgnoreCaseAndEmpresa(nome, empresa, pageable);

        if (clientes.isEmpty()) {
            throw new ClienteNaoLocalizadoException("Nenhum cliente encontrado.");
        }

        return clientes.map(clienteMapper::toResponse);
    }

    public Page<ClienteResponseDTO> buscarTodosClientes(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return clienteRepository.findAllByEmpresa(empresa, pageable)
                .map(clienteMapper::toResponse);
    }

    public Page<ClienteResponseDTO> buscarTodosAtivos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return clienteRepository.findByAtivoAndEmpresa(true, empresa, pageable)
                .map(clienteMapper::toResponse);
    }

    public Page<ClienteResponseDTO> buscarTodosInativos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return clienteRepository.findByAtivoAndEmpresa(false, empresa, pageable)
                .map(clienteMapper::toResponse);
    }

    /* =====================================================
       MÉTODOS PRIVADOS
       ===================================================== */

    private Empresa obterEmpresaDoUsuarioLogado() {
        return localizarUsuario().getEmpresa();
    }

    private Users localizarUsuario() {

        return usersRepository
                .findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado())
                .orElseThrow(() ->
                        new UsuarioNaoLocalizadoException(
                                "Usuário não localizado."
                        ));
    }

    private void validarDuplicidadeCadastro(
            Empresa empresa,
            String cnpj,
            String email) {

        if (clienteRepository
                .existsByCpfCnpjAndEmpresa(cnpj, empresa))
            throw new DuplicidadeCnpjCpfException("CNPJ/CPF já cadastrado em sistema.");

        if (clienteRepository
                .existsByEmailAndEmpresa(email, empresa))
            throw new DuplicidadeEmailClienteException("Email já cadastrado para um produto.");
    }

    private Cliente localizarClienteCpfCnpj(Empresa empresa, String cpfCnpj) {

        return clienteRepository
                .findByCpfCnpjAndEmpresa(cpfCnpj, empresa)
                .orElseThrow(() -> new ClienteNaoLocalizadoException("Cliente não encontrado"));
    }
    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String email,
            String telefone) {

        if (email != null &&
                clienteRepository
                        .existsByEmailAndEmpresaAndIdNot(
                                email, empresa, id))
            throw new DuplicidadeEmailClienteException("Email já cadastrado em sistema.");

        if (telefone != null &&
                clienteRepository
                        .existsByTelefoneAndEmpresaAndIdNot(
                                telefone, empresa, id))
            throw new DuplicidadeTelefoneClienteException("Telefone já cadastrado em sistema.");
    }
}
