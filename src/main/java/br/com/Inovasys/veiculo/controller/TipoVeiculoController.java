package br.com.Inovasys.veiculo.controller;

import br.com.Inovasys.veiculo.dto.AtualizarTipoVeiculoDTO;
import br.com.Inovasys.veiculo.dto.CadastrarTipoVeiculoDTO;
import br.com.Inovasys.veiculo.dto.TipoVeiculoResponseDTO;
import br.com.Inovasys.veiculo.service.TipoVeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipo-veiculo")
public class TipoVeiculoController {

    private final TipoVeiculoService tipoVeiculoService;

    public TipoVeiculoController(TipoVeiculoService tipoVeiculoService){
        this.tipoVeiculoService = tipoVeiculoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TipoVeiculoResponseDTO> cadastrarTipoVeiculo(
            @RequestBody @Valid CadastrarTipoVeiculoDTO cadastrarTipoVeiculoDTO){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.cadastrarTipoVeiculo(cadastrarTipoVeiculoDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<TipoVeiculoResponseDTO> atualizarTipoVeiculo(
            @RequestBody @Valid AtualizarTipoVeiculoDTO atualizarTipoVeiculoDTO){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.atualizarTipoVeiculo(atualizarTipoVeiculoDTO));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> desativarTipoVeiculo(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.desativarTipoVeiculo(id));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<TipoVeiculoResponseDTO> ativarTipoVeiculo(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.ativarTipoVeiculo(id));
    }

    @GetMapping("/buscar/{tipo}")
    public ResponseEntity<Page<TipoVeiculoResponseDTO>> buscarTipoVeiculo(@PathVariable String tipo,
             @PageableDefault(page = 0, size = 20, sort = "tipoVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.buscarTipoVeiculo(tipo, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<TipoVeiculoResponseDTO>> buscarTodos(
            @PageableDefault(page = 0, size = 20, sort = "tipoVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.buscarTodosTipos(pageable));
    }

    @GetMapping("/buscar/ativo")
    public ResponseEntity<Page<TipoVeiculoResponseDTO>> buscarTiposAtivos(
            @PageableDefault(page = 0, size = 20, sort = "tipoVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.buscarTiposAtivos(pageable));
    }

    @GetMapping("/buscar/inativo")
    public ResponseEntity<Page<TipoVeiculoResponseDTO>> buscarTiposInativos(
            @PageableDefault(page = 0, size = 20, sort = "tipoVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoVeiculoService.buscarTiposInativos(pageable));
    }
}