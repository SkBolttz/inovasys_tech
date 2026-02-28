package br.com.Inovasys.modulos.gestaoOficina.veiculo.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarTipoVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarTipoVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.TipoVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoVeiculo;
import br.com.Inovasys.infra.exceptions.VeiculosException.DuplicidadeTipoVeiculoException;
import br.com.Inovasys.infra.exceptions.VeiculosException.TipoVeiculoNaoLocalizadoException;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.TipoVeiculoMapper;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.TipoVeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoVeiculoService {

    private final TipoVeiculoRepository tipoVeiculoRepository;
    private final UsersRepository usersRepository;
    private final TipoVeiculoMapper tipoVeiculoMapper;

    public TipoVeiculoService(
            TipoVeiculoRepository tipoVeiculoRepository,
            UsersRepository usersRepository,
            TipoVeiculoMapper tipoVeiculoMapper) {

        this.tipoVeiculoRepository = tipoVeiculoRepository;
        this.usersRepository = usersRepository;
        this.tipoVeiculoMapper = tipoVeiculoMapper;
    }

    public TipoVeiculoResponseDTO cadastrarTipoVeiculo(
            @Valid CadastrarTipoVeiculoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidade(dto.tipoVeiculo(), empresa);

        TipoVeiculo tipo = tipoVeiculoMapper.toEntity(dto);
        tipo.setEmpresa(empresa);

        tipoVeiculoRepository.save(tipo);
        return tipoVeiculoMapper.toResponse(tipo);
    }

    public TipoVeiculoResponseDTO atualizarTipoVeiculo(
            @Valid AtualizarTipoVeiculoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidadeUpdate(
                dto.idTipoVeiculo(),
                empresa,
                dto.tipoVeiculo());

        TipoVeiculo tipo =
                localizarTipoId(dto.idTipoVeiculo(), empresa);

        if (dto.tipoVeiculo() != null) {
            tipo.setTipoVeiculo(dto.tipoVeiculo());
        }

        tipoVeiculoRepository.save(tipo);
        return tipoVeiculoMapper.toResponse(tipo);
    }

    public TipoVeiculoResponseDTO desativarTipoVeiculo(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        TipoVeiculo tipo = localizarTipoId(id, empresa);

        tipo.setAtivo(false);
        tipoVeiculoRepository.save(tipo);

        return tipoVeiculoMapper.toResponse(tipo);
    }

    public TipoVeiculoResponseDTO ativarTipoVeiculo(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        TipoVeiculo tipo = localizarTipoId(id, empresa);

        tipo.setAtivo(true);
        tipoVeiculoRepository.save(tipo);

        return tipoVeiculoMapper.toResponse(tipo);
    }

    public Page<TipoVeiculoResponseDTO> buscarTipoVeiculo(String nome, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return tipoVeiculoRepository.findByTipoVeiculoContainingIgnoreCaseAndEmpresa(nome, empresa, pageable)
                .map(tipoVeiculoMapper::toResponse);
    }

    public Page<TipoVeiculoResponseDTO> buscarTodosTipos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoVeiculoRepository
                .findAllByEmpresa(empresa, pageable)
                .map(tipoVeiculoMapper::toResponse);
    }

    public Page<TipoVeiculoResponseDTO> buscarTiposAtivos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoVeiculoRepository
                .findByAtivoAndEmpresa(true, empresa, pageable)
                .map(tipoVeiculoMapper::toResponse);
    }

    public Page<TipoVeiculoResponseDTO> buscarTiposInativos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoVeiculoRepository
                .findByAtivoAndEmpresa(false, empresa, pageable)
                .map(tipoVeiculoMapper::toResponse);
    }

    // ==========================
    // MÉTODOS PRIVADOS
    // ==========================

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

    private void validarDuplicidade(
            String nome,
            Empresa empresa) {

        if (tipoVeiculoRepository
                .existsByTipoVeiculoAndEmpresa(nome, empresa)) {

            throw new DuplicidadeTipoVeiculoException(
                    "Tipo de veículo já cadastrado no sistema."
            );
        }
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String nome) {

        if (nome != null &&
                tipoVeiculoRepository
                        .existsByTipoVeiculoAndEmpresaAndIdNot(
                                nome, empresa, id)) {

            throw new DuplicidadeTipoVeiculoException(
                    "Tipo de veículo já cadastrado no sistema."
            );
        }
    }

    private TipoVeiculo localizarTipoId(
            Long id,
            Empresa empresa) {

        return tipoVeiculoRepository
                .findByIdAndEmpresa(id, empresa)
                .orElseThrow(() ->
                        new TipoVeiculoNaoLocalizadoException(
                                "Tipo de veículo não localizado."
                        ));
    }
}