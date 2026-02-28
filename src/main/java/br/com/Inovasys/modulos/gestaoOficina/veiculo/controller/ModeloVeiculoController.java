package br.com.Inovasys.modulos.gestaoOficina.veiculo.controller;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarModeloVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarModeloVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.ModeloVeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.service.ModeloVeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modelo-veiculo")
public class ModeloVeiculoController {

    private final ModeloVeiculoService modeloVeiculoService;

    public ModeloVeiculoController(ModeloVeiculoService modeloVeiculoService){
        this.modeloVeiculoService = modeloVeiculoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ModeloVeiculoResponseDTO> cadastrarModelo(
            @RequestBody @Valid CadastrarModeloVeiculoDTO cadastrarModeloVeiculoDTO){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.cadastrarModelo(cadastrarModeloVeiculoDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ModeloVeiculoResponseDTO> atualizarModelo(
            @RequestBody @Valid AtualizarModeloVeiculoDTO atualizarModeloVeiculoDTO){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.atualizarModelo(atualizarModeloVeiculoDTO));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<ModeloVeiculoResponseDTO> desativarModelo(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.desativarModelo(id));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<ModeloVeiculoResponseDTO> ativarModelo(@PathVariable Long id){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.ativarModelo(id));
    }

    @GetMapping("/buscar/{modelo}")
    public ResponseEntity<Page<ModeloVeiculoResponseDTO>> buscarModelo(@PathVariable String modelo,
            @PageableDefault(page = 0, size = 20, sort = "modeloVeiculo")Pageable pageable){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.buscarModelo(modelo, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ModeloVeiculoResponseDTO>> buscarTodos(
            @PageableDefault(page = 0, size = 20, sort = "modeloVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.buscarTodosModelos(pageable));
    }

    @GetMapping("/buscar/ativos")
    public ResponseEntity<Page<ModeloVeiculoResponseDTO>> buscarModelosAtivos(
            @PageableDefault(page = 0, size = 20, sort = "modeloVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.buscarModelosAtivos(pageable));
    }

    @GetMapping("/buscar/inativo")
    public ResponseEntity<Page<ModeloVeiculoResponseDTO>> buscarModelosInativos(
            @PageableDefault(page = 0, size = 20, sort = "modeloVeiculo") Pageable pageable){
        return ResponseEntity.ok()
                .body(modeloVeiculoService.buscarModelosInativos(pageable));
    }
}