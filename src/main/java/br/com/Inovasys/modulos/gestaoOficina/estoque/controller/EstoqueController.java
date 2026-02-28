package br.com.Inovasys.modulos.gestaoOficina.estoque.controller;

import br.com.Inovasys.modulos.gestaoOficina.estoque.dto.AtualizarEstoqueDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.dto.CadastroEstoqueDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.dto.EstoqueResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.estoque.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EstoqueResponseDTO> cadastrarItemEstoque(@RequestBody @Valid CadastroEstoqueDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.cadastrarItemEstoque(dto));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<EstoqueResponseDTO> atualizarItemEstoque(@RequestBody @Valid AtualizarEstoqueDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.atualizarItemEstoque(dto));
    }

    @PatchMapping("/ativar/{idItem}")
    public ResponseEntity<EstoqueResponseDTO> ativarItemEstoque(@PathVariable Long idItem) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(estoqueService.ativarItemEstoque(idItem));
    }

    @PatchMapping("/desativar/{idItem}")
    public ResponseEntity<EstoqueResponseDTO> desativarItemEstoque(@PathVariable Long idItem) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(estoqueService.desativarItemEstoque(idItem));
    }

    @GetMapping("/buscar/codigo/{codigoItem}")
    public ResponseEntity<EstoqueResponseDTO> buscarItemEstoque(@PathVariable String codigoItem) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.buscarItemEstoque(codigoItem));
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<Page<EstoqueResponseDTO>> buscarItemEstoquePorNome(@PathVariable String nome,
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.buscarItemEstoquePorNome(nome, pageable));
    }

    @GetMapping("/buscar/ativos")
    public ResponseEntity<Page<EstoqueResponseDTO>> buscarItensEstoqueAtivos(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.buscarItensEstoqueAtivos(pageable));
    }

    @GetMapping("/buscar/inativos")
    public ResponseEntity<Page<EstoqueResponseDTO>> buscarItensEstoqueInativos(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.buscarItensEstoqueInativos(pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<EstoqueResponseDTO>> buscarTodosItensEstoque(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.buscarTodosItens(pageable));
    }
}
