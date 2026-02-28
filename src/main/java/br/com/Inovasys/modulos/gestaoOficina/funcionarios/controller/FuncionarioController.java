package br.com.Inovasys.modulos.gestaoOficina.funcionarios.controller;

import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.AtualizarFuncionarioDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.CadastrarFuncionarioDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.FuncionarioResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(@RequestBody CadastrarFuncionarioDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(funcionarioService.cadastrarFuncionario(dto));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(@RequestBody AtualizarFuncionarioDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.atualizarFuncionario(dto));
    }

    @PatchMapping("/ativar/{cpf}")
    public ResponseEntity<FuncionarioResponseDTO> ativarFuncionario(@PathVariable String cpf) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(funcionarioService.ativarFuncionario(cpf));
    }

    @PatchMapping("/desativar/{cpf}")
    public ResponseEntity<FuncionarioResponseDTO> inativarFuncionario(@PathVariable String cpf) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(funcionarioService.inativarFuncionario(cpf));
    }

    @GetMapping("/buscar/cpf/{cpf}")
    public ResponseEntity<FuncionarioResponseDTO> buscarFuncionario(@PathVariable String cpf) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.buscarFuncionarioPorCPF(cpf));
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscarFuncionarioPorNome(@PathVariable String nome,
            @PageableDefault(page = 0, size = 10, sort = "nome") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.buscarFuncionarioPorNome(nome, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscarTodosFuncionarios(
            @PageableDefault(page = 0, size = 10, sort = "nome") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.buscarTodosFuncionarios(pageable));
    }

    @GetMapping("/buscar/ativo")
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscarFuncionariosAtivos(
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.buscarTodosFuncionariosAtivos(pageable));
    }

    @GetMapping("/buscar/inativo")
    public ResponseEntity<Page<FuncionarioResponseDTO>> buscarFuncionariosInativos(
            @PageableDefault(page = 0, size = 20, sort = "nome") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(funcionarioService.buscarTodosFuncionariosInativos(pageable));
    }
}
