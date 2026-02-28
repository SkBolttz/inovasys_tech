package br.com.Inovasys.veiculo.controller;

import br.com.Inovasys.veiculo.dto.AtualizarTipoCombustivelDTO;
import br.com.Inovasys.veiculo.dto.CadastrarTipoCombustivelDTO;
import br.com.Inovasys.veiculo.dto.TipoCombustivelResponseDTO;
import br.com.Inovasys.veiculo.service.TipoCombustivelService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipo-combustivel")
public class TipoCombustivelController {

    private final TipoCombustivelService tipoCombustivelService;

    public TipoCombustivelController(TipoCombustivelService tipoCombustivelService){
        this.tipoCombustivelService = tipoCombustivelService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TipoCombustivelResponseDTO> cadastrarTipoCombustivel(
            @RequestBody @Valid CadastrarTipoCombustivelDTO cadastrarTipoCombustivelDTO){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.cadastrarTipoCombustivel(cadastrarTipoCombustivelDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<TipoCombustivelResponseDTO> atualizarTipoCombustivel(
            @RequestBody @Valid AtualizarTipoCombustivelDTO atualizarTipoCombustivelDTO){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.atualizarTipoCombustivel(atualizarTipoCombustivelDTO));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<TipoCombustivelResponseDTO> desativarTipoCombustivel(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.desativarTipoCombustivel(id));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<TipoCombustivelResponseDTO> ativarTipoCombustivel(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.ativarTipoCombustivel(id));
    }

    @GetMapping("/buscar/{tipo}")
    public ResponseEntity<Page<TipoCombustivelResponseDTO>> buscarTipoCombustivel(@PathVariable String tipo,
            @PageableDefault(page = 0, size = 20, sort = "tipoCombustivel") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.buscarTipoCombustivel(tipo, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<TipoCombustivelResponseDTO>> buscarTodos(
            @PageableDefault(page = 0, size = 20, sort = "tipoCombustivel") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.buscarTodosTipos(pageable));
    }

    @GetMapping("/buscar/ativo")
    public ResponseEntity<Page<TipoCombustivelResponseDTO>> buscarTiposAtivos(
            @PageableDefault(page = 0, size = 20, sort = "tipoCombustivel") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.buscarTiposAtivos(pageable));
    }

    @GetMapping("/buscar/inativo")
    public ResponseEntity<Page<TipoCombustivelResponseDTO>> buscarTiposInativos(
            @PageableDefault(page = 0, size = 20, sort = "tipoCombustivel") Pageable pageable){
        return ResponseEntity.ok()
                .body(tipoCombustivelService.buscarTiposInativos(pageable));
    }
}