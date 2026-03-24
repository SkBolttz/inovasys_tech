package br.com.Inovasys.modulos.gestaoOficina.veiculo.service;

import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.infra.security.ObterUsuarioLogado;
import br.com.Inovasys.infra.exceptions.ClienteException.ClienteNaoLocalizadoException;
import br.com.Inovasys.modulos.gestaoOficina.cliente.entity.Cliente;
import br.com.Inovasys.modulos.gestaoOficina.cliente.repository.ClienteRepository;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.VeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.ModeloVeiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoCombustivel;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoVeiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.Veiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.*;
import br.com.Inovasys.infra.exceptions.VeiculosException.DuplicidadeVeiculoException;
import br.com.Inovasys.infra.exceptions.VeiculosException.VeiculoNaoLocalizadoException;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.mapper.VeiculoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;
    private final ModeloVeiculoRepository modeloRepository;
    private final TipoVeiculoRepository tipoRepository;
    private final TipoCombustivelRepository combustivelRepository;
    private final UsersRepository usersRepository;
    private final VeiculoMapper veiculoMapper;

    public VeiculoService(
            VeiculoRepository veiculoRepository,
            ClienteRepository clienteRepository,
            ModeloVeiculoRepository modeloRepository,
            TipoVeiculoRepository tipoRepository,
            TipoCombustivelRepository combustivelRepository,
            UsersRepository usersRepository,
            VeiculoMapper veiculoMapper) {

        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
        this.modeloRepository = modeloRepository;
        this.tipoRepository = tipoRepository;
        this.combustivelRepository = combustivelRepository;
        this.usersRepository = usersRepository;
        this.veiculoMapper = veiculoMapper;
    }

    public VeiculoResponseDTO cadastrarVeiculo(CadastrarVeiculoDTO dto) {

        Empresa empresa = obterEmpresaUsuarioLogado();

        if (veiculoRepository.existsByPlacaAndEmpresa(dto.placa(), empresa)) {
            throw new DuplicidadeVeiculoException(
                    "Já existe um veículo cadastrado com essa placa."
            );
        }

        Cliente cliente = clienteRepository
                .findByIdAndEmpresa(dto.idCliente(), empresa)
                .orElseThrow(() ->
                        new ClienteNaoLocalizadoException("Cliente não localizado."));

        ModeloVeiculo modelo = modeloRepository
                .findByIdAndEmpresa(dto.idModelo(), empresa)
                .orElseThrow(() -> new RuntimeException("Modelo não localizado."));

        TipoVeiculo tipo = tipoRepository
                .findByIdAndEmpresa(dto.idTipoVeiculo(), empresa)
                .orElseThrow(() -> new RuntimeException("Tipo de veículo não localizado."));

        TipoCombustivel combustivel = combustivelRepository
                .findByIdAndEmpresa(dto.idTipoCombustivel(), empresa)
                .orElseThrow(() -> new RuntimeException("Tipo de combustível não localizado."));

        Veiculo veiculo = veiculoMapper.toEntity(dto);

        veiculo.setEmpresa(empresa);
        veiculo.setCliente(cliente);
        veiculo.setModelo(modelo);
        veiculo.setTipo(tipo);
        veiculo.setAtivo(true);
        veiculo.setCombustivel(combustivel);

        veiculoRepository.save(veiculo);

        return veiculoMapper.toResponse(veiculo);
    }

    public VeiculoResponseDTO atualizarVeiculo(AtualizarVeiculoDTO dto) {

        Empresa empresa = obterEmpresaUsuarioLogado();
        Veiculo veiculo = localizarPorPlaca(dto.placa(), empresa);

        if (dto.idCliente() != null) {
            Cliente cliente = clienteRepository
                    .findByIdAndEmpresa(dto.idCliente(), empresa)
                    .orElseThrow(() ->
                            new ClienteNaoLocalizadoException("Cliente não localizado."));
            veiculo.setCliente(cliente);
        }

        if (dto.idModelo() != null) {
            ModeloVeiculo modelo = modeloRepository
                    .findByIdAndEmpresa(dto.idModelo(), empresa)
                    .orElseThrow(() -> new RuntimeException("Modelo não localizado."));
            veiculo.setModelo(modelo);
        }

        if (dto.idTipoVeiculo() != null) {
            TipoVeiculo tipo = tipoRepository
                    .findByIdAndEmpresa(dto.idTipoVeiculo(), empresa)
                    .orElseThrow(() -> new RuntimeException("Tipo de veículo não localizado."));
            veiculo.setTipo(tipo);
        }

        if (dto.idTipoCombustivel() != null) {
            TipoCombustivel combustivel = combustivelRepository
                    .findByIdAndEmpresa(dto.idTipoCombustivel(), empresa)
                    .orElseThrow(() -> new RuntimeException("Combustível não localizado."));
            veiculo.setCombustivel(combustivel);
        }

        if (dto.cor() != null) veiculo.setCor(dto.cor());
        if (dto.ano() != null) veiculo.setAno(dto.ano());

        veiculoRepository.save(veiculo);

        return veiculoMapper.toResponse(veiculo);
    }

    public VeiculoResponseDTO ativarVeiculo(Long id) {
        Empresa empresa = obterEmpresaUsuarioLogado();
        Veiculo veiculo = localizarPorId(id, empresa);

        veiculo.setAtivo(true);
        veiculoRepository.save(veiculo);

        return veiculoMapper.toResponse(veiculo);
    }

    public VeiculoResponseDTO desativarVeiculo(Long id) {
        Empresa empresa = obterEmpresaUsuarioLogado();
        Veiculo veiculo = localizarPorId(id, empresa);

        veiculo.setAtivo(false);
        veiculoRepository.save(veiculo);

        return veiculoMapper.toResponse(veiculo);
    }

    public VeiculoResponseDTO buscarVeiculoPorPlaca(String placa) {
        Empresa empresa = obterEmpresaUsuarioLogado();
        return veiculoMapper.toResponse(localizarPorPlaca(placa, empresa));
    }

    public Page<VeiculoResponseDTO> buscarVeiculosPorNomeCliente(
            String nome,
            Pageable pageable) {

        Empresa empresa = obterEmpresaUsuarioLogado();

        return veiculoRepository
                .findByCliente_NomeContainingIgnoreCaseAndEmpresa(
                        nome, empresa, pageable)
                .map(veiculoMapper::toResponse);
    }

    public Page<VeiculoResponseDTO> buscarTodos(Pageable pageable) {

        Empresa empresa = obterEmpresaUsuarioLogado();

        return veiculoRepository
                .findAllByEmpresa(empresa, pageable)
                .map(veiculoMapper::toResponse);
    }

    public Page<VeiculoResponseDTO> buscarVeiculosAtivos(Pageable pageable) {

        Empresa empresa = obterEmpresaUsuarioLogado();

        return veiculoRepository
                .findByAtivoAndEmpresa(true, empresa, pageable)
                .map(veiculoMapper::toResponse);
    }

    public Page<VeiculoResponseDTO> buscarVeiculosInativos(Pageable pageable) {

        Empresa empresa = obterEmpresaUsuarioLogado();

        return veiculoRepository
                .findByAtivoAndEmpresa(false, empresa, pageable)
                .map(veiculoMapper::toResponse);
    }

    public Page<VeiculoResponseDTO> buscarVeiculoDoCliente(Long idCliente, Pageable pageable){

        Empresa empresa = obterEmpresaUsuarioLogado();

        return veiculoRepository
                .findByClienteIdAndEmpresa(idCliente, empresa, pageable)
                .map(veiculoMapper::toResponse);
    }

    // ==========================

    private Empresa obterEmpresaUsuarioLogado() {
        return usersRepository
                .findByCpf(ObterUsuarioLogado.obterCpfUsuarioLogado())
                .orElseThrow(() ->
                        new UsuarioNaoLocalizadoException("Usuário não localizado."))
                .getEmpresa();
    }

    private Veiculo localizarPorPlaca(String placa, Empresa empresa) {
        return veiculoRepository
                .findByPlacaAndEmpresa(placa, empresa)
                .orElseThrow(() ->
                        new VeiculoNaoLocalizadoException("Veículo não localizado."));
    }

    private Veiculo localizarPorId(Long id, Empresa empresa) {
        return veiculoRepository
                .findByIdAndEmpresa(id, empresa)
                .orElseThrow(() ->
                        new VeiculoNaoLocalizadoException("Veículo não localizado."));
    }
}