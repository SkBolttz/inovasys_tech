package br.com.Inovasys.modulos.gestaoOficina.os.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.UsuarioNaoLocalizadoException;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.infra.exceptions.ClienteException.ClienteNaoLocalizadoException;
import br.com.Inovasys.infra.exceptions.FuncionarioException.FuncionarioNaoLocalizadoException;
import br.com.Inovasys.infra.exceptions.OSException.*;
import br.com.Inovasys.infra.exceptions.ServicoException.ServicoNaoLocalizadoException;
import br.com.Inovasys.infra.exceptions.VeiculosException.VeiculoNaoLocalizadoException;
import br.com.Inovasys.infra.security.ObterUsuarioLogado;
import br.com.Inovasys.modulos.gestaoOficina.cliente.entity.Cliente;
import br.com.Inovasys.modulos.gestaoOficina.cliente.repository.ClienteRepository;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.empresa.repository.EmpresaRepository;
import br.com.Inovasys.modulos.gestaoOficina.estoque.entity.Estoque;
import br.com.Inovasys.modulos.gestaoOficina.estoque.repository.EstoqueRepository;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.entity.Funcionario;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.repository.FuncionarioRepository;
import br.com.Inovasys.modulos.gestaoOficina.itemEstoqueOs.entity.ItemEstoqueOS;
import br.com.Inovasys.modulos.gestaoOficina.itemServicoOS.entity.ItemServicoOS;
import br.com.Inovasys.modulos.gestaoOficina.os.dto.*;
import br.com.Inovasys.modulos.gestaoOficina.os.entity.OrdemServico;
import br.com.Inovasys.modulos.gestaoOficina.os.enuns.Status;
import br.com.Inovasys.modulos.gestaoOficina.os.mapper.OrdemServicoMapper;
import br.com.Inovasys.modulos.gestaoOficina.os.repository.OrdemServicoRepository;
import br.com.Inovasys.modulos.gestaoOficina.servicos.entity.Servico;
import br.com.Inovasys.modulos.gestaoOficina.servicos.repository.ServicoRepository;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.Veiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdemServicoService {

    private final OrdemServicoRepository osRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EstoqueRepository estoqueRepository;
    private final ServicoRepository servicoRepository;
    private final OrdemServicoMapper osMapper;
    private final UsersRepository usersRepository;


    public OrdemServicoService(
            OrdemServicoRepository osRepository,
            UsersRepository usersRepository,
            ClienteRepository clienteRepository,
            VeiculoRepository veiculoRepository,
            FuncionarioRepository funcionarioRepository,
            EstoqueRepository estoqueRepository,
            ServicoRepository servicoRepository,
            OrdemServicoMapper osMapper) {

        this.osRepository = osRepository;
        this.usersRepository = usersRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.estoqueRepository = estoqueRepository;
        this.servicoRepository = servicoRepository;
        this.osMapper = osMapper;
    }

    // =========================================================
    // CRIAR ORDEM DE SERVIÇO
    // =========================================================

    public OrdemServicoResponseDTO criarOrdemServico(CriarOrdemServicoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Cliente cliente = localizarCliente(empresa, dto.cpfCnpj());
        Veiculo veiculo = localizarVeiculo(empresa, dto.placaVeiculo());
        Funcionario funcionario = localizarFuncionario(empresa, dto.cpfFuncionarioResponsavel());

        OrdemServico os = osMapper.toEntity(dto);
        os.setNumero(gerarNumeroOS(empresa));
        os.setEmpresa(empresa);
        os.setCliente(cliente);
        os.setVeiculo(veiculo);
        os.setFuncionarioResponsavel(funcionario);
        os.setDataAbertura(LocalDateTime.now());
        os.setStatus(Status.ABERTA);
        os.setAtivo(true);
        os.setPrazoEntrega(dto.prazoEntrega()); // Adição realizada para controle de prazo
        os.setDescricaoProblema(dto.descricaoProblema());

        // LISTAS MUTÁVEIS
        os.setServicos(new ArrayList<>());
        os.setProdutos(new ArrayList<>());

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    // =========================================================
    // ADICIONAR SERVIÇO
    // =========================================================

    public OrdemServicoResponseDTO adicionarServicoOS(AdicionarServicoOSDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(dto.osId(), empresa);
        validarOSAlteravel(os);

        Servico servico = servicoRepository.findByIdAndEmpresa(dto.idServico(), empresa)
                .orElseThrow(() -> new ServicoNaoLocalizadoException("Serviço não encontrado"));

        boolean jaExiste = os.getServicos().stream()
                .anyMatch(s -> s.getServico().getId().equals(servico.getId()));

        if (jaExiste) {
            throw new ServicoDuplicadoOSException("Serviço já adicionado a esta OS");
        }

        ItemServicoOS item = new ItemServicoOS();
        item.setServico(servico);
        item.setQuantidade(1);
        item.setValorUnitario(servico.getValorMaoDeObra());
        item.setOrdemServico(os);

        os.getServicos().add(item);
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    // =========================================================
    // ADICIONAR PRODUTO / ESTOQUE
    // =========================================================

    public OrdemServicoResponseDTO adicionarProdutoOS(AdicionarProdutoOSDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(dto.osId(), empresa);
        validarOSAlteravel(os);

        Estoque produto = estoqueRepository.findByIdAndEmpresa(dto.idProduto(), empresa)
                .orElseThrow(() -> new ProdutoNaoLocalizadoException("Produto não encontrado"));

        if (!produto.getAtivo()) {
            throw new StatusProdutoException("Produto inativo");
        }

        if (produto.getEstoqueAtual() < dto.quantidade()) {
            throw new QuantidadeEstoqueException("Quantidade solicitada maior que o estoque disponível");
        }

        ItemEstoqueOS item = new ItemEstoqueOS();
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setValorUnitario(produto.getPrecoVenda());
        item.setValorTotal(produto.getPrecoVenda().multiply(BigDecimal.valueOf(dto.quantidade())));
        item.setOrdemServico(os);

        produto.setEstoqueAtual(produto.getEstoqueAtual() - dto.quantidade());

        os.getProdutos().add(item);
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    // =========================================================
    // REMOVER SERVIÇO / PRODUTO
    // =========================================================

    public OrdemServicoResponseDTO removerServicoOS(Long osId, Long itemServicoId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);
        validarOSAlteravel(os);

        os.getServicos().removeIf(s -> s.getId().equals(itemServicoId));
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    public OrdemServicoResponseDTO removerProdutoOS(Long osId, Long itemEstoqueId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);
        validarOSAlteravel(os);

        ItemEstoqueOS item = os.getProdutos().stream()
                .filter(p -> p.getId().equals(itemEstoqueId))
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoLocalizadoException("Produto não encontrado na OS"));

        Estoque produto = item.getProduto();
        produto.setEstoqueAtual(produto.getEstoqueAtual() + item.getQuantidade());

        os.getProdutos().remove(item);
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    // =========================================================
    // STATUS DA OS
    // =========================================================

    public OrdemServicoResponseDTO iniciarOS(Long osId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);

        if (os.getStatus() != Status.ABERTA) {
            throw new StatusOSException("Somente OS em ABERTA podem ser iniciadas");
        }

        os.setStatus(Status.EM_EXECUCAO);
        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    public OrdemServicoResponseDTO aguardarPecaOS(Long osId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);

        if (os.getStatus() != Status.EM_EXECUCAO) {
            throw new StatusOSException("Somente OS em execução podem aguardar peça");
        }

        os.setStatus(Status.AGUARDANDO_PECA);
        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    public OrdemServicoResponseDTO finalizarOS(FinalizarOsDTO finalizarOsDTO) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(finalizarOsDTO.osId(), empresa);

        if (os.getStatus() == Status.FINALIZADA) {
            throw new StatusOSException("OS já finalizada");
        }

        os.setQuilometragemSaida(finalizarOsDTO.quilometragemSaida());
        os.setDiagnostico(finalizarOsDTO.diagnostico());
        os.setFormaPagamento(finalizarOsDTO.formaPagamento());
        os.setParcelas(finalizarOsDTO.parcelas());
        os.setGarantiaDias(finalizarOsDTO.garantiaDias());

        if(finalizarOsDTO.desconto() != null) {
            os.setDesconto(finalizarOsDTO.desconto());
        }

        os.setStatus(Status.FINALIZADA);
        os.setDataConclusao(LocalDateTime.now());

        atualizarValoresOS(os);
        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    public OrdemServicoResponseDTO cancelarOS(Long osId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);

        if (os.getStatus() == Status.FINALIZADA) {
            throw new StatusOSException("Não é possível cancelar OS finalizada");
        }

        os.setStatus(Status.CANCELADA);

        os.getProdutos().forEach(item -> {
            Estoque produto = item.getProduto();
            produto.setEstoqueAtual(produto.getEstoqueAtual() + item.getQuantidade());
        });

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    // =========================================================
    // CONSULTAS
    // =========================================================

    public Page<OrdemServicoResponseDTO> listarOrdensDeServico(Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<OrdemServico> ordens = osRepository.findByEmpresa(empresa, pageable);

        if (ordens.isEmpty()) {
            throw new OSNaoLocalizadaException("Nenhuma ordem de serviço encontrada");
        }

        return ordens.map(osMapper::toResponse);
    }

    public Page<OrdemServicoResponseDTO> listarOrdensDeServicoPorStatus(Status status, Pageable pageable) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<OrdemServico> ordens = osRepository.findByEmpresaAndStatus(empresa, status, pageable);

        if (ordens.isEmpty()) {
            throw new OSNaoLocalizadaException("Nenhuma OS encontrada com o status informado");
        }

        return ordens.map(osMapper::toResponse);
    }

    public OrdemServicoResponseDTO listarOrdensDeServicoPorId(Long osId) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(osId, empresa);

        return osMapper.toResponse(os);
    }

    public Page<OrdemServicoResponseDTO> listarOsDoFuncionario(
            String cpfFuncionario,
            Status status,
            Pageable pageable
    ) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Funcionario funcionario = localizarFuncionario(empresa, cpfFuncionario);

        Page<OrdemServico> os;

        if (status == null) {
            os = osRepository.findByEmpresaAndFuncionarioResponsavel(empresa, funcionario, pageable);
        } else {
            os = osRepository.findByEmpresaAndFuncionarioResponsavelAndStatus(
                    empresa, funcionario, status, pageable
            );
        }

        if (os.isEmpty()) {
            String msg = (status == null)
                    ? "Nenhuma ordem de serviço encontrada para o funcionário informado"
                    : "Nenhuma ordem de serviço com status " + status + " encontrada para o funcionário informado";

            throw new OSNaoLocalizadaException(msg);
        }

        return os.map(osMapper::toResponse);
    }


    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    private OrdemServico localizarOS(Long osId, Empresa empresa) {
        return osRepository.findByIdAndEmpresa(osId, empresa)
                .orElseThrow(() -> new OSNaoLocalizadaException("Ordem de serviço não encontrada"));
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

    private Cliente localizarCliente(Empresa empresa, String cpfCnpj) {
        return clienteRepository.findByCpfCnpjAndEmpresa(cpfCnpj, empresa)
                .orElseThrow(() -> new ClienteNaoLocalizadoException("Cliente não encontrado"));
    }

    private Veiculo localizarVeiculo(Empresa empresa, String placa) {
        return veiculoRepository.findByPlacaAndEmpresa(placa, empresa)
                .orElseThrow(() -> new VeiculoNaoLocalizadoException("Veículo não encontrado"));
    }

    private Funcionario localizarFuncionario(Empresa empresa, String cpf) {
        return funcionarioRepository.findByCpfAndEmpresa(cpf, empresa)
                .orElseThrow(() -> new FuncionarioNaoLocalizadoException("Funcionário não encontrado"));
    }

    private void validarOSAlteravel(OrdemServico os) {
        if (os.getStatus() == Status.FINALIZADA || os.getStatus() == Status.CANCELADA) {
            throw new StatusOSException("Não é possível alterar OS finalizada ou cancelada");
        }
    }

    private void atualizarValoresOS(OrdemServico os) {

        BigDecimal valorServicos = os.getServicos().stream()
                .map(s -> s.getValorUnitario().multiply(BigDecimal.valueOf(s.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorProdutos = os.getProdutos().stream()
                .map(p -> p.getValorUnitario().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal desconto = os.getDesconto() != null ? os.getDesconto() : BigDecimal.ZERO;

        os.setValorServicos(valorServicos);
        os.setValorProdutos(valorProdutos);
        os.setValorTotal(valorServicos.add(valorProdutos).subtract(desconto));
    }

    private String gerarNumeroOS(Empresa empresa) {
        long proximoNumero = osRepository.count() + 1;
        return "OS-" + empresa.getId() + "-" + String.format("%05d", proximoNumero);
    }
}