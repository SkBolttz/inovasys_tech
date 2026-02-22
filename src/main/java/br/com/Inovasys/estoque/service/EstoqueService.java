package br.com.Inovasys.estoque.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.*;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.estoque.dto.AtualizarEstoqueDTO;
import br.com.Inovasys.estoque.dto.CadastroEstoqueDTO;
import br.com.Inovasys.estoque.dto.EstoqueResponseDTO;
import br.com.Inovasys.estoque.entity.Estoque;
import br.com.Inovasys.estoque.exception.DuplicidadeCodigoException;
import br.com.Inovasys.estoque.exception.DuplicidadeDescricaoException;
import br.com.Inovasys.estoque.exception.EstoqueNuloException;
import br.com.Inovasys.estoque.exception.ItemEstoqueNaoLocalizadoException;
import br.com.Inovasys.estoque.mapper.EstoqueMapper;
import br.com.Inovasys.estoque.repository.EstoqueRepository;
import br.com.Inovasys.funcionarios.entity.Funcionario;
import br.com.Inovasys.funcionarios.exception.FuncionarioNaoLocalizadoException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final UsersRepository usersRepository;
    private final EstoqueMapper estoqueMapper;

    public EstoqueService(EstoqueRepository estoqueRepository, UsersRepository usersRepository, EstoqueMapper estoqueMapper){
        this.estoqueRepository = estoqueRepository;
        this.usersRepository = usersRepository;
        this.estoqueMapper = estoqueMapper;
    }

    public EstoqueResponseDTO cadastrarItemEstoque(@Valid CadastroEstoqueDTO dto) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        validarDuplicidadeCadastro(empresa, dto.descricao(), dto.codigo());

        Estoque itemEstoque = estoqueMapper.toEntity(dto);
        itemEstoque.setEmpresa(empresa);
        itemEstoque.setAtivo(true);
        itemEstoque.setEstoqueAtual(itemEstoque.getEstoqueAtual() == null || itemEstoque.getEstoqueAtual() < 0 ? 0
                : itemEstoque.getEstoqueAtual());
        itemEstoque.setUltimaReposicao(LocalDate.now());

        estoqueRepository.save(itemEstoque);
        return estoqueMapper.toResponse(itemEstoque);
    }

    public EstoqueResponseDTO atualizarItemEstoque(@Valid AtualizarEstoqueDTO dto) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Estoque itemEstoque = localizarItemEstoque(dto.idItem(), empresa);

        validarDuplicidadeUpdate(dto.idItem(), empresa, dto.descricao(), dto.codigo());

        itemEstoque.setDescricao(dto.descricao() == null ? itemEstoque.getDescricao() : dto.descricao());
        itemEstoque.setCodigo(dto.codigo() == null ? itemEstoque.getCodigo() : dto.codigo());
        itemEstoque.setPrecoVenda(dto.precoVenda() == null ? itemEstoque.getPrecoVenda() : dto.precoVenda());
        itemEstoque.setPrecoCompra(dto.precoCompra() == null ? itemEstoque.getPrecoCompra() : dto.precoCompra());
        itemEstoque
                .setEstoqueMinimo(dto.estoqueMinimo() == null ? itemEstoque.getEstoqueMinimo() : dto.estoqueMinimo());
        itemEstoque
                .setEstoqueMaximo(dto.estoqueMaximo() == null ? itemEstoque.getEstoqueMaximo() : dto.estoqueMaximo());
        itemEstoque
                .setUnidadeMedida(dto.unidadeMedida() == null ? itemEstoque.getUnidadeMedida() : dto.unidadeMedida());

        // Garantir que estoqueAtual não fique negativo
        if (dto.estoqueAtual() < 0) {
            throw new EstoqueNuloException("Estoque atual nao pode ser negativo");
        } else {
            itemEstoque
                    .setEstoqueAtual(dto.estoqueAtual() == 0 ? itemEstoque.getEstoqueAtual() : dto.estoqueAtual());
        }

        if (dto.estoqueAtual() > itemEstoque.getEstoqueAtual()) {
            itemEstoque.setUltimaReposicao(LocalDate.now());
        }

        estoqueRepository.save(itemEstoque);
        return estoqueMapper.toResponse(itemEstoque);
    }

    public EstoqueResponseDTO ativarItemEstoque(Long idItem) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Estoque itemEstoque = localizarItemEstoque(idItem, empresa);
        itemEstoque.setAtivo(true);
        estoqueRepository.save(itemEstoque);
        return estoqueMapper.toResponse(itemEstoque);
    }

    public EstoqueResponseDTO desativarItemEstoque(Long idItem) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Estoque itemEstoque = localizarItemEstoque(idItem, empresa);
        itemEstoque.setAtivo(false);
        estoqueRepository.save(itemEstoque);
        return estoqueMapper.toResponse(itemEstoque);
    }

    public EstoqueResponseDTO buscarItemEstoque(String codigoItem) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();

        Estoque itemEstoque = localizarItemCodigo(codigoItem, empresa);
        return estoqueMapper.toResponse(itemEstoque);
    }

    public Page<EstoqueResponseDTO> buscarItemEstoquePorNome(String nome, Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<Estoque> itensEstoque = estoqueRepository.findByDescricaoContainingIgnoreCaseAndEmpresa(nome, empresa,
                pageable);

        if (itensEstoque.isEmpty()) {
            throw new ItemEstoqueNaoLocalizadoException("Item de estoque não encontrado");
        }

        return itensEstoque.map(estoqueMapper::toResponse);
    }

    public Page<EstoqueResponseDTO> buscarItensEstoqueAtivos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<Estoque> itensEstoqueAtivos = estoqueRepository.findByAtivoAndEmpresa(true, empresa, pageable);
        return itensEstoqueAtivos.map(estoqueMapper::toResponse);
    }

    public Page<EstoqueResponseDTO> buscarItensEstoqueInativos(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<Estoque> itensEstoqueInativos = estoqueRepository.findByAtivoAndEmpresa(false, empresa, pageable);
        return itensEstoqueInativos.map(estoqueMapper::toResponse);
    }

    public Page<EstoqueResponseDTO> buscarTodosItens(Pageable pageable) {
        Empresa empresa = obterEmpresaDoUsuarioLogado();
        Page<Estoque> itensEstoque = estoqueRepository.findByEmpresa(empresa, pageable);
        return itensEstoque.map(estoqueMapper::toResponse);
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
            String descricao,
            String codigo) {

        if (estoqueRepository
                .existsByDescricaoAndEmpresa(descricao, empresa))
            throw new DuplicidadeDescricaoException("Descrição já cadastrada para um produto.");

        if (estoqueRepository
                .existsByCodigoAndEmpresa(codigo, empresa))
            throw new DuplicidadeCodigoException("Código já cadastrado para um produto.");
    }

    private void validarDuplicidadeUpdate(
            Long id,
            Empresa empresa,
            String descricao,
            String codigo) {

        if (descricao != null &&
                estoqueRepository
                        .existsByDescricaoAndEmpresaAndIdNot(
                                descricao, empresa, id))
            throw new DuplicidadeDescricaoException("Descrição já cadastrada para um produto.");

        if (codigo != null &&
                estoqueRepository
                        .existsByCodigoAndEmpresaAndIdNot(
                                codigo, empresa, id))
            throw new DuplicidadeCodigoException("Código já cadastrado para um produto.");
    }

    private Estoque localizarItemEstoque(Long id, Empresa empresa) {
        return estoqueRepository.findByIdAndEmpresa(id, empresa)
                .orElseThrow(() -> new ItemEstoqueNaoLocalizadoException("Item de estoque não encontrado"));
    }

    private Estoque localizarItemCodigo(String codigoItem, Empresa empresa) {
        return estoqueRepository.findByCodigoAndEmpresa(codigoItem, empresa)
                .orElseThrow(() -> new ItemEstoqueNaoLocalizadoException("Item de estoque não encontrado"));
    }
}
