package br.com.Inovasys.modulos.gestaoOficina.os.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.Inovasys.modulos.gestaoOficina.os.entity.AvariaOS;
import br.com.Inovasys.modulos.gestaoOficina.os.entity.OrdemServico;
import br.com.Inovasys.modulos.gestaoOficina.os.enuns.Status;
import br.com.Inovasys.modulos.gestaoOficina.os.mapper.AvariaOSMapper;
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
    private final AvariaOSMapper avariaOsMapper;


    public OrdemServicoService(
            OrdemServicoRepository osRepository,
            UsersRepository usersRepository,
            ClienteRepository clienteRepository,
            VeiculoRepository veiculoRepository,
            FuncionarioRepository funcionarioRepository,
            EstoqueRepository estoqueRepository,
            ServicoRepository servicoRepository,
            OrdemServicoMapper osMapper,
            AvariaOSMapper avariaOSMapper) {

        this.osRepository = osRepository;
        this.usersRepository = usersRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.estoqueRepository = estoqueRepository;
        this.servicoRepository = servicoRepository;
        this.osMapper = osMapper;
        this.avariaOsMapper = avariaOSMapper;
    }

    // =========================================================
    // CRIAR ORDEM DE SERVIÇO
    // =========================================================

    public OrdemServicoResponseDTO criarOrdemServico(CriarOrdemServicoDTO dto) {

        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Cliente cliente = localizarCliente(empresa, dto.idCliente());
        Veiculo veiculo = localizarVeiculo(empresa, dto.idVeiculo());
        Funcionario funcionario = localizarFuncionario(empresa, dto.idFuncionario());

        OrdemServico os = osMapper.toEntity(dto);

        // Configurações Básicas
        os.setNumero(gerarNumeroOS(empresa));
        os.setEmpresa(empresa);
        os.setCliente(cliente);
        os.setVeiculo(veiculo);
        os.setFuncionarioResponsavel(funcionario);
        os.setDataAbertura(LocalDateTime.now());
        os.setStatus(Status.ABERTA);
        os.setAtivo(true);

        // Mapeamento das Avarias (O "Mapa" de cliques na imagem)
        if (dto.avarias() != null && !dto.avarias().isEmpty()) {
            List<AvariaOS> listaAvarias = dto.avarias().stream()
                    .map(avariaDto -> {
                        AvariaOS avaria = avariaOsMapper.toEntity(avariaDto);
                        avaria.setOrdemServico(os);
                        return avaria;
                    }).collect(Collectors.toList());
            os.setAvarias(listaAvarias);
        } else {
            os.setAvarias(new ArrayList<>());
        }

        // Inicialização de Listas Mutáveis para Itens
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

        // Localiza o funcionário executor, se informado no DTO
        Funcionario executor = null;
        if (dto.idFuncionarioExecutor() != null) {
            executor = localizarFuncionario(empresa, dto.idFuncionarioExecutor());
        }

        boolean jaExiste = os.getServicos().stream()
                .anyMatch(s -> s.getServico().getId().equals(servico.getId()));

        if (jaExiste) {
            throw new ServicoDuplicadoOSException("Serviço já adicionado a esta OS");
        }

        ItemServicoOS item = getItemServicoOS(dto, servico, os);

        os.setFuncionarioResponsavel(executor);
        os.getServicos().add(item);
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    private static ItemServicoOS getItemServicoOS(AdicionarServicoOSDTO dto, Servico servico, OrdemServico os) {
        ItemServicoOS item = new ItemServicoOS();
        item.setServico(servico);
        item.setOrdemServico(os);

        // --- LÓGICA DE FLEXIBILIDADE ---
        // Se o DTO trouxe quantidade, usa ela; senão, padrão 1.
        item.setQuantidade(dto.quantidade() != null ? dto.quantidade() : 1);

        // Se o DTO trouxe valor aplicado (negociado), usa ele; senão, usa o valor padrão do cadastro.
        item.setValorUnitario(dto.valorAplicado() != null ? dto.valorAplicado() : servico.getValorMaoDeObra());
        return item;
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

        // Define qual valor será usado: o do DTO (negociado) ou o do Cadastro (padrão)
        BigDecimal precoFinal = dto.valorAplicado() != null ? dto.valorAplicado() : produto.getPrecoVenda();

        ItemEstoqueOS item = new ItemEstoqueOS();
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        item.setValorUnitario(precoFinal);
        item.setValorTotal(precoFinal.multiply(BigDecimal.valueOf(dto.quantidade())));
        item.setOrdemServico(os);

        // Baixa automática no estoque
        produto.setEstoqueAtual(produto.getEstoqueAtual() - dto.quantidade());

        os.getProdutos().add(item);
        atualizarValoresOS(os); // Garante que o valorTotal da OS seja recalculado

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

        // Localiza o item específico dentro da lista da OS
        ItemServicoOS item = os.getServicos().stream()
                .filter(s -> s.getId().equals(itemServicoId))
                .findFirst()
                .orElseThrow(() -> new ServicoNaoLocalizadoException("Serviço não encontrado nesta Ordem de Serviço"));

        os.getServicos().remove(item);
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

        // Devolve a quantidade ao estoque antes de remover o item
        Estoque produto = item.getProduto();
        produto.setEstoqueAtual(produto.getEstoqueAtual() + item.getQuantidade());

        os.getProdutos().remove(item);
        atualizarValoresOS(os);

        osRepository.save(os);
        return osMapper.toResponse(os);
    }

    public OrdemServicoResponseDTO atualizarQuantidadeProdutoOS(AtualizarQuantidadeProdutoOS atualizarQuantidadeProdutoOS) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        OrdemServico os = localizarOS(atualizarQuantidadeProdutoOS.idOs(), empresa);
        validarOSAlteravel(os);

        ItemEstoqueOS item = os.getProdutos().stream()
                .filter(p -> p.getId().equals(atualizarQuantidadeProdutoOS.idProduto()))
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoLocalizadoException("Produto não encontrado na OS"));

        if (atualizarQuantidadeProdutoOS.quantidade() <= 0) {
            return removerProdutoOS(atualizarQuantidadeProdutoOS.idOs(), atualizarQuantidadeProdutoOS.idProduto());
        }

        Estoque produto = item.getProduto();
        int diferenca = atualizarQuantidadeProdutoOS.quantidade() - item.getQuantidade();

        // Se estiver aumentando, verifica se tem estoque para o que falta
        if (diferenca > 0 && produto.getEstoqueAtual() < diferenca) {
            throw new QuantidadeEstoqueException("Estoque insuficiente para aumentar a quantidade deste produto");
        }

        // Ajusta o estoque base: se diferença for positiva, subtrai do estoque. Se negativa, soma (devolve).
        produto.setEstoqueAtual(produto.getEstoqueAtual() - diferenca);

        // Atualiza o item
        item.setQuantidade(atualizarQuantidadeProdutoOS.quantidade());
        item.setValorTotal(item.getValorUnitario().multiply(BigDecimal.valueOf(atualizarQuantidadeProdutoOS.quantidade())));

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
            Long id,
            Status status,
            Pageable pageable
    ) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Funcionario funcionario = localizarFuncionario(empresa, id);

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

    private Cliente localizarCliente(Empresa empresa, Long id) {
        return clienteRepository.findByIdAndEmpresa(id, empresa)
                .orElseThrow(() -> new ClienteNaoLocalizadoException("Cliente não encontrado"));
    }

    private Veiculo localizarVeiculo(Empresa empresa, Long id) {
        return veiculoRepository.findByIdAndEmpresa(id, empresa)
                .orElseThrow(() -> new VeiculoNaoLocalizadoException("Veículo não encontrado"));
    }

    private Funcionario localizarFuncionario(Empresa empresa, Long id) {
        return funcionarioRepository.findByIdAndEmpresa(id, empresa)
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