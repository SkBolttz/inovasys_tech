package br.com.Inovasys.estoque.service;

import br.com.Inovasys.auth.entity.Users;
import br.com.Inovasys.auth.exception.*;
import br.com.Inovasys.auth.repository.UsersRepository;
import br.com.Inovasys.auth.util.ObterUsuarioLogado;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.estoque.dto.AtualizarEstoqueDTO;
import br.com.Inovasys.estoque.dto.CadastroEstoqueDTO;
import br.com.Inovasys.estoque.repository.EstoqueRepository;
import br.com.Inovasys.funcionarios.entity.Funcionario;
import br.com.Inovasys.funcionarios.exception.FuncionarioNaoLocalizadoException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final UsersRepository usersRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, UsersRepository usersRepository){
        this.estoqueRepository = estoqueRepository;
        this.usersRepository = usersRepository;
    }

    public EmpresaResponseDTO cadastrarItemEstoque(@Valid CadastroEstoqueDTO dto) {
    }

    public EmpresaResponseDTO atualizarItemEstoque(@Valid AtualizarEstoqueDTO dto) {
    }

    public EmpresaResponseDTO ativarItemEstoque(Long idItem) {
    }

    public EmpresaResponseDTO desativarItemEstoque(Long idItem) {
    }

    public EmpresaResponseDTO buscarItemEstoque(String codigoItem) {
    }

    public Page<EmpresaResponseDTO> buscarItemEstoquePorNome(String nome, Pageable pageable) {
    }

    public Page<EmpresaResponseDTO> buscarItensEstoqueAtivos(Pageable pageable) {
    }

    public Page<EmpresaResponseDTO> buscarItensEstoqueInativos(Pageable pageable) {
    }

    public Page<EmpresaResponseDTO> buscarTodosItens(Pageable pageable) {
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
                .existsDescricaoAndEmpresa(descricao, empresa))
            throw new EmailDuplicadoException("E-mail já cadastrado");

        if (estoqueRepository
                .existsByCodigoAndEmpresa(codigo, empresa))
            throw new CPFDuplicadoException("CPF já cadastrado");
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
            throw new EmailDuplicadoException("E-mail já cadastrado");

        if (codigo != null &&
                estoqueRepository
                        .existsByCodigoAndEmpresaAndIdNot(
                                codigo, empresa, id))
            throw new TelefoneDuplicadoException("Telefone já cadastrado");
    }
}
