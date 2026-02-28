package br.com.Inovasys.modulos.gestaoOficina.veiculo.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarTipoCombustivelDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarTipoCombustivelDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.TipoCombustivelResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoCombustivel;
import br.com.Inovasys.infra.Exceptions.VeiculosException.DuplicidadeTipoCombustivelException;
import br.com.Inovasys.infra.Exceptions.VeiculosException.TipoCombustivelNaoLocalizadoException;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.TipoCombustivelMapper;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.TipoCombustivelRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TipoCombustivelService {

    private final TipoCombustivelRepository tipoCombustivelRepository;
    private final UsersRepository usersRepository;
    private final TipoCombustivelMapper tipoCombustivelMapper;

    public TipoCombustivelService(
            TipoCombustivelRepository tipoCombustivelRepository,
            UsersRepository usersRepository,
            TipoCombustivelMapper tipoCombustivelMapper) {

        this.tipoCombustivelRepository = tipoCombustivelRepository;
        this.usersRepository = usersRepository;
        this.tipoCombustivelMapper = tipoCombustivelMapper;
    }

    public TipoCombustivelResponseDTO cadastrarTipoCombustivel(
            @Valid CadastrarTipoCombustivelDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidade(dto.tipoCombustivel(), empresa);

        TipoCombustivel tipo = tipoCombustivelMapper.toEntity(dto);
        tipo.setEmpresa(empresa);

        tipoCombustivelRepository.save(tipo);
        return tipoCombustivelMapper.toResponse(tipo);
    }

    public TipoCombustivelResponseDTO atualizarTipoCombustivel(
            @Valid AtualizarTipoCombustivelDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidadeUpdate(
                dto.idTipoCombustivel(),
                empresa,
                dto.tipoCombustivel());

        TipoCombustivel tipo =
                localizarTipoId(dto.idTipoCombustivel(), empresa);

        if (dto.tipoCombustivel() != null) {
            tipo.setTipoCombustivel(dto.tipoCombustivel());
        }

        tipoCombustivelRepository.save(tipo);
        return tipoCombustivelMapper.toResponse(tipo);
    }

    public TipoCombustivelResponseDTO desativarTipoCombustivel(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        TipoCombustivel tipo = localizarTipoId(id, empresa);

        tipo.setAtivo(false);
        tipoCombustivelRepository.save(tipo);

        return tipoCombustivelMapper.toResponse(tipo);
    }

    public TipoCombustivelResponseDTO ativarTipoCombustivel(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        TipoCombustivel tipo = localizarTipoId(id, empresa);

        tipo.setAtivo(true);
        tipoCombustivelRepository.save(tipo);

        return tipoCombustivelMapper.toResponse(tipo);
    }

    public Page<TipoCombustivelResponseDTO> buscarTipoCombustivel(String nome, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return tipoCombustivelRepository.findByTipoCombustivelContainingIgnoreCaseAndEmpresa(nome, empresa, pageable)
                .map(tipoCombustivelMapper::toResponse);
    }

    public Page<TipoCombustivelResponseDTO> buscarTodosTipos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoCombustivelRepository
                .findAllByEmpresa(empresa, pageable)
                .map(tipoCombustivelMapper::toResponse);
    }

    public Page<TipoCombustivelResponseDTO> buscarTiposAtivos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoCombustivelRepository
                .findByAtivoAndEmpresa(true, empresa, pageable)
                .map(tipoCombustivelMapper::toResponse);
    }

    public Page<TipoCombustivelResponseDTO> buscarTiposInativos(
            Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return tipoCombustivelRepository
                .findByAtivoAndEmpresa(false, empresa, pageable)
                .map(tipoCombustivelMapper::toResponse);
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

        if (tipoCombustivelRepository
                .existsByTipoCombustivelAndEmpresa(nome, empresa)) {

            throw new DuplicidadeTipoCombustivelException(
                    "Tipo de combustível já cadastrado no sistema."
            );
        }
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String nome) {

        if (nome != null &&
                tipoCombustivelRepository
                        .existsByTipoCombustivelAndEmpresaAndIdNot(
                                nome, empresa, id)) {

            throw new DuplicidadeTipoCombustivelException(
                    "Tipo de combustível já cadastrado no sistema."
            );
        }
    }

    private TipoCombustivel localizarTipoId(
            Long id,
            Empresa empresa) {

        return tipoCombustivelRepository
                .findByIdAndEmpresa(id, empresa)
                .orElseThrow(() ->
                        new TipoCombustivelNaoLocalizadoException(
                                "Tipo de combustível não localizado."
                        ));
    }
}