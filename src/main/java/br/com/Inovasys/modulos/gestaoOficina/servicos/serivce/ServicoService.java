package br.com.Inovasys.modulos.gestaoOficina.servicos.serivce;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.infra.exceptions.ServicoException.ServicoDuplicadoException;
import br.com.Inovasys.infra.exceptions.ServicoException.ServicoNaoLocalizadoException;
import br.com.Inovasys.infra.security.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.AtualizarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.CadastrarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.ServicoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.entity.Servico;
import br.com.Inovasys.modulos.gestaoOficina.servicos.mapper.ServicoMapper;
import br.com.Inovasys.modulos.gestaoOficina.servicos.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;
    private final UsersRepository usersRepository;

    public ServicoService(
            ServicoRepository servicoRepository,
            ServicoMapper servicoMapper,
            UsersRepository usersRepository) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
        this.usersRepository = usersRepository;
    }

    /*
     * ======================
     * Cadastro
     * ======================
     */

    public ServicoResponseDTO cadastrarServico(CadastrarServicoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        validarDescricaoDuplicada(dto.descricao(), empresa);

        Servico servico = servicoMapper.toEntity(dto);
        servico.setEmpresa(empresa);
        servico.setAtivo(true);

        return servicoMapper.toDto(servicoRepository.save(servico));
    }

    /*
     * ======================
     * Atualizar
     * ======================
     */

    public ServicoResponseDTO atualizarServico(AtualizarServicoDTO dto) {

        Servico servico = buscarServicoEmpresa(dto.idServico());
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        if (dto.descricao() != null &&
                !dto.descricao().equalsIgnoreCase(servico.getDescricao())) {
            validarDescricaoDuplicada(dto.descricao(), empresa);
        }

        if (dto.descricao() != null) {
            servico.setDescricao(dto.descricao());
        }
        if (dto.observacao() != null) {
            servico.setObservacao(dto.observacao());
        }
        if (dto.valorMaoDeObra() != null) {
            servico.setValorMaoDeObra(dto.valorMaoDeObra());
        }
        if (dto.tempoEstimado() != null) {
            servico.setTempoEstimado(dto.tempoEstimado());
        }

        servicoRepository.save(servico);

        return servicoMapper.toDto(servico);
    }

    /*
     * ======================
     * Ativar / Desativar
     * ======================
     */

    public ServicoResponseDTO ativarServico(Long idServico) {
        Servico servico = buscarServicoEmpresa(idServico);
        servico.setAtivo(true);
        servicoRepository.save(servico);
        return servicoMapper.toDto(servico);
    }

    public ServicoResponseDTO desativarServico(Long idServico) {
        Servico servico = buscarServicoEmpresa(idServico);
        servico.setAtivo(false);
        servicoRepository.save(servico);
        return servicoMapper.toDto(servico);
    }

    /*
     * ======================
     * Buscar
     * ======================
     */

    public Page<ServicoResponseDTO> buscarPorDescricao(String descricao, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Page<Servico> servicos = servicoRepository.findByDescricaoContainingIgnoreCaseAndEmpresa(descricao, empresa,
                pageable);

        if (servicos.isEmpty()) {
            throw new ServicoNaoLocalizadoException("Nenhum serviço encontrado");
        }

        return servicos.map(servicoMapper::toDto);
    }

    public Page<ServicoResponseDTO> buscarTodos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return servicoRepository.findByEmpresa(empresa, pageable)
                .map(servicoMapper::toDto);
    }

    public Page<ServicoResponseDTO> buscarAtivos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return servicoRepository.findByEmpresaAndAtivo(empresa, true, pageable)
                .map(servicoMapper::toDto);
    }

    public Page<ServicoResponseDTO> buscarInativos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return servicoRepository.findByEmpresaAndAtivo(empresa, false, pageable)
                .map(servicoMapper::toDto);
    }

    /*
     * ======================
     * Métodos privados
     * ======================
     */

    private Users localizarUsuario() {

        return usersRepository
                .findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado())
                .orElseThrow(() ->
                        new UsuarioNaoLocalizadoException(
                                "Usuário não localizado."
                        ));
    }

    private Empresa obterEmpresaDoUsuarioLogado() {
        return localizarUsuario().getEmpresa();
    }

    private Servico buscarServicoEmpresa(Long idServico) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return servicoRepository.findByIdAndEmpresa(idServico,empresa)
                .orElseThrow(() -> new ServicoNaoLocalizadoException("Serviço não encontrado"));
    }

    private void validarDescricaoDuplicada(String descricao, Empresa empresa) {
        if (servicoRepository.existsByDescricaoIgnoreCaseAndEmpresa(descricao, empresa)) {
            throw new ServicoDuplicadoException("Já existe serviço cadastrado com esta descrição");
        }
    }
}
