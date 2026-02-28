package br.com.Inovasys.modulos.gestaoOficina.veiculo.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarModeloVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarModeloVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.ModeloVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.MarcaVeiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.ModeloVeiculo;
import br.com.Inovasys.infra.exceptions.VeiculosException.DuplicidadeModeloException;
import br.com.Inovasys.infra.exceptions.VeiculosException.MarcaNaoLocalizadaException;
import br.com.Inovasys.infra.exceptions.VeiculosException.ModeloNaoLocalizadoException;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.ModeloVeiculoMapper;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.MarcaVeiculoRepository;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.ModeloVeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ModeloVeiculoService {

    private final ModeloVeiculoRepository modeloVeiculoRepository;
    private final UsersRepository usersRepository;
    private final ModeloVeiculoMapper modeloVeiculoMapper;
    private final MarcaVeiculoRepository marcaVeiculoRepository;

    public ModeloVeiculoService(ModeloVeiculoRepository modeloVeiculoRepository,
                                UsersRepository usersRepository,
                                ModeloVeiculoMapper modeloVeiculoMapper,
                                MarcaVeiculoRepository marcaVeiculoRepository) {
        this.modeloVeiculoRepository = modeloVeiculoRepository;
        this.usersRepository = usersRepository;
        this.modeloVeiculoMapper = modeloVeiculoMapper;
        this.marcaVeiculoRepository = marcaVeiculoRepository;
    }

    public ModeloVeiculoResponseDTO cadastrarModelo(
            @Valid CadastrarModeloVeiculoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidade(dto.modeloVeiculo(), empresa);
        MarcaVeiculo marcaVeiculo = localizarMarca(dto.idMarca(), empresa);

        ModeloVeiculo modelo = modeloVeiculoMapper.toEntity(dto);
        modelo.setEmpresa(empresa);
        modelo.setMarcaVeiculo(marcaVeiculo);

        modeloVeiculoRepository.save(modelo);
        return modeloVeiculoMapper.toResponse(modelo);
    }

    public ModeloVeiculoResponseDTO atualizarModelo(
            @Valid AtualizarModeloVeiculoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidadeUpdate(dto.idModelo(), empresa, dto.modeloVeiculo());

        ModeloVeiculo modelo = localizarModeloId(dto.idModelo(), empresa);
        MarcaVeiculo marcaVeiculo = localizarMarca(dto.idMarca(), empresa);

        validarModeloMarca(marcaVeiculo, modelo, empresa);
        modelo.setMarcaVeiculo(marcaVeiculo);
        if (dto.modeloVeiculo() != null) {
            modelo.setModeloVeiculo(dto.modeloVeiculo());
        }

        modeloVeiculoRepository.save(modelo);
        return modeloVeiculoMapper.toResponse(modelo);
    }

    public ModeloVeiculoResponseDTO desativarModelo(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        ModeloVeiculo modelo = localizarModeloId(id, empresa);

        modelo.setAtivo(false);
        modeloVeiculoRepository.save(modelo);

        return modeloVeiculoMapper.toResponse(modelo);
    }

    public ModeloVeiculoResponseDTO ativarModelo(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        ModeloVeiculo modelo = localizarModeloId(id, empresa);

        modelo.setAtivo(true);
        modeloVeiculoRepository.save(modelo);

        return modeloVeiculoMapper.toResponse(modelo);
    }

    public Page<ModeloVeiculoResponseDTO> buscarModelo(String nomeModelo, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return modeloVeiculoRepository.findByModeloVeiculoContainingIgnoreCaseAndEmpresa(nomeModelo, empresa, pageable)
                .map(modeloVeiculoMapper::toResponse);
    }

    public Page<ModeloVeiculoResponseDTO> buscarTodosModelos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return modeloVeiculoRepository
                .findAllByEmpresa(empresa, pageable)
                .map(modeloVeiculoMapper::toResponse);
    }

    public Page<ModeloVeiculoResponseDTO> buscarModelosAtivos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return modeloVeiculoRepository
                .findByAtivoAndEmpresa(true, empresa, pageable)
                .map(modeloVeiculoMapper::toResponse);
    }

    public Page<ModeloVeiculoResponseDTO> buscarModelosInativos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return modeloVeiculoRepository
                .findByAtivoAndEmpresa(false, empresa, pageable)
                .map(modeloVeiculoMapper::toResponse);
    }

    // ===============================
    // Métodos privados auxiliares
    // ===============================

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

    private void validarDuplicidade(String nomeModelo, Empresa empresa) {
        if (modeloVeiculoRepository
                .existsByModeloVeiculoAndEmpresa(nomeModelo, empresa)) {

            throw new DuplicidadeModeloException(
                    "Modelo já cadastrado no sistema."
            );
        }
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String nomeModelo) {

        if (nomeModelo != null &&
                modeloVeiculoRepository
                        .existsByModeloVeiculoAndEmpresaAndIdNot(
                                nomeModelo, empresa, id)) {

            throw new DuplicidadeModeloException(
                    "Modelo já cadastrado no sistema."
            );
        }
    }

    private ModeloVeiculo localizarModeloId(Long id, Empresa empresa) {
        return modeloVeiculoRepository
                .findByIdAndEmpresa(id, empresa)
                .orElseThrow(() ->
                        new ModeloNaoLocalizadoException(
                                "Modelo não localizado no sistema."
                        ));
    }

    private MarcaVeiculo localizarMarca(Long id, Empresa empresa){
        return marcaVeiculoRepository.findByIdAndEmpresa(id, empresa).orElseThrow(
                () -> new MarcaNaoLocalizadaException("Marca não localizada."));
    }

    private void validarModeloMarca(MarcaVeiculo marcaVeiculo, ModeloVeiculo modeloVeiculo, Empresa empresa){
        if(modeloVeiculoRepository.findByMarcaVeiculoAndEmpresa(marcaVeiculo, empresa)){
            throw new DuplicidadeModeloException("Modelo já cadastrado para esta marca!");
        }
    }
}