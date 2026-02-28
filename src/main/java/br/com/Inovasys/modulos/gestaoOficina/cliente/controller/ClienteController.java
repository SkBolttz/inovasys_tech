package br.com.Inovasys.modulos.gestaoOficina.cliente.controller;

import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteAtualizarDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteCadastroDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    /*
     * ===========================
     * CADASTRO / ATUALIZAÇÃO
     * ===========================
     */

    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteCadastroDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteService.cadastrarCliente(dto));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@RequestBody @Valid ClienteAtualizarDTO dto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(clienteService.editarCliente(dto));
    }

    @PutMapping("/ativar/{cnpjCpf}")
    public ResponseEntity<ClienteResponseDTO> ativarCliente(@PathVariable String cnpjCpf) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(clienteService.ativarCliente(cnpjCpf));
    }

    @PutMapping("/desativar/{cnpjCpf}")
    public ResponseEntity<ClienteResponseDTO> desativarCliente(@PathVariable String cnpjCpf) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(clienteService.desativarCliente(cnpjCpf));
    }

    /*
     * ===========================
     * BUSCAS (PAGINADAS)
     * ===========================
     */

    @GetMapping("/buscar/cpf-cnpj/{cpfCnpj}")
    public ResponseEntity<ClienteResponseDTO> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        return ResponseEntity.ok(
                clienteService.buscarClientePorCpfCnpj(cpfCnpj));
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarPorNome(@PathVariable String nome,
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(
                clienteService.buscarClientePorNome(nome, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodosClientes(
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(
                clienteService.buscarTodosClientes(pageable));
    }

    @GetMapping("/buscar/ativos")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodosClientesAtivos(
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(
                clienteService.buscarTodosAtivos(pageable));
    }

    @GetMapping("/buscar/inativos")
    public ResponseEntity<Page<ClienteResponseDTO>> buscarTodosClientesInativos(
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(
                clienteService.buscarTodosInativos(pageable));
    }

}
