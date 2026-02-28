package br.com.Inovasys.modulos.gestaoOficina.veiculo.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarMarcaVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarMarcaVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.MarcaVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.MarcaVeiculo;
import br.com.Inovasys.infra.exceptions.VeiculosException.DuplicidadeMarcaException;
import br.com.Inovasys.infra.exceptions.VeiculosException.MarcaNaoLocalizadaException;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.MarcaVeiculoMapper;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.MarcaVeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MarcaVeiculoService {

    private final MarcaVeiculoRepository marcaVeiculoRepository;
    private final UsersRepository usersRepository;
    private final MarcaVeiculoMapper marcaVeiculoMapper;

    public MarcaVeiculoService(MarcaVeiculoRepository marcaVeiculoRepository, UsersRepository usersRepository,
                               MarcaVeiculoMapper marcaVeiculoMapper){
        this.marcaVeiculoRepository = marcaVeiculoRepository;
        this.usersRepository = usersRepository;
        this.marcaVeiculoMapper = marcaVeiculoMapper;
    }

    public MarcaVeiculoResponseDTO cadastrarMarca(@Valid CadastrarMarcaVeiculoDTO cadastrarMarcaVeiculoDTO) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidade(cadastrarMarcaVeiculoDTO.nomeMarca(), empresa);

        MarcaVeiculo marcaVeiculo = marcaVeiculoMapper.toEntity(cadastrarMarcaVeiculoDTO);
        marcaVeiculo.setEmpresa(empresa);
        marcaVeiculo.setAtivo(true);
        marcaVeiculoRepository.save(marcaVeiculo);

        return marcaVeiculoMapper.toResponse(marcaVeiculo);
    }

    public MarcaVeiculoResponseDTO atualizarMarca(@Valid AtualizarMarcaVeiculoDTO atualizarMarcaVeiculoDTO) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidadeUpdate(atualizarMarcaVeiculoDTO.idMarca(), empresa, atualizarMarcaVeiculoDTO.nomeMarca());
        MarcaVeiculo marcaVeiculo = localizarMarcaId(atualizarMarcaVeiculoDTO.idMarca(), empresa);

        if(atualizarMarcaVeiculoDTO.nomeMarca() != null){
            marcaVeiculo.setNomeMarca(atualizarMarcaVeiculoDTO.nomeMarca());
        }

        marcaVeiculoRepository.save(marcaVeiculo);
        return marcaVeiculoMapper.toResponse(marcaVeiculo);
    }

    public MarcaVeiculoResponseDTO desativarMarca(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        MarcaVeiculo marcaVeiculo = localizarMarcaId(id, empresa);
        marcaVeiculo.setAtivo(false);
        marcaVeiculoRepository.save(marcaVeiculo);
        return marcaVeiculoMapper.toResponse(marcaVeiculo);
    }

    public MarcaVeiculoResponseDTO ativarMarca(Long id) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        MarcaVeiculo marcaVeiculo = localizarMarcaId(id, empresa);
        marcaVeiculo.setAtivo(true);
        marcaVeiculoRepository.save(marcaVeiculo);
        return marcaVeiculoMapper.toResponse(marcaVeiculo);
    }

    public Page<MarcaVeiculoResponseDTO> buscarMarca(String marca, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        return marcaVeiculoRepository.findByNomeMarcaContainingIgnoreCaseAndEmpresa(marca, empresa, pageable)
                .map(marcaVeiculoMapper::toResponse);
    }

    public Page<MarcaVeiculoResponseDTO> buscarTodasMarcas(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return marcaVeiculoRepository.findAllByEmpresa(empresa, pageable)
                .map(marcaVeiculoMapper::toResponse);
    }

    public Page<MarcaVeiculoResponseDTO> buscarMarcasAtivas(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return marcaVeiculoRepository.findByAtivoAndEmpresa(true, empresa, pageable)
                .map(marcaVeiculoMapper::toResponse);
    }

    public Page<MarcaVeiculoResponseDTO> buscarMarcasInativas(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        return marcaVeiculoRepository.findByAtivoAndEmpresa(false, empresa, pageable)
                .map(marcaVeiculoMapper::toResponse);
    }

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

    private void validarDuplicidade(String nomeMarca, Empresa empresa){
        if(marcaVeiculoRepository.existsByNomeMarcaAndEmpresa(nomeMarca, empresa)){
            throw new DuplicidadeMarcaException("Marca já cadastrada em sistema.");
        }
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String nomeMarca) {

        if (nomeMarca != null &&
                marcaVeiculoRepository
                        .existsByNomeMarcaAndEmpresaAndIdNot(
                                nomeMarca, empresa, id))
            throw new DuplicidadeMarcaException("Marca já cadastrado em sistema.");
    }

    private MarcaVeiculo localizarMarcaId(Long id, Empresa empresa){
        return marcaVeiculoRepository.findByIdAndEmpresa(id, empresa).orElseThrow(
                () -> new MarcaNaoLocalizadaException("Marca não localizada em sistema."));
    }
}
