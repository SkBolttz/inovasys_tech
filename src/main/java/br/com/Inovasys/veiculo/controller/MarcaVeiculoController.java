package br.com.Inovasys.veiculo.controller;

import br.com.Inovasys.veiculo.dto.AtualizarMarcaVeiculoDTO;
import br.com.Inovasys.veiculo.dto.CadastrarMarcaVeiculoDTO;
import br.com.Inovasys.veiculo.dto.MarcaVeiculoResponseDTO;
import br.com.Inovasys.veiculo.service.MarcaVeiculoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marca-veiculo")
public class MarcaVeiculoController {

    private final MarcaVeiculoService marcaVeiculoService;

    public MarcaVeiculoController(MarcaVeiculoService marcaVeiculoService){
        this.marcaVeiculoService = marcaVeiculoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<MarcaVeiculoResponseDTO> cadastrarMarca(@RequestBody @Valid CadastrarMarcaVeiculoDTO cadastrarMarcaVeiculoDTO){
        return ResponseEntity.ok().body(marcaVeiculoService.cadastrarMarca(cadastrarMarcaVeiculoDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<MarcaVeiculoResponseDTO> atualizarMarca(@RequestBody @Valid AtualizarMarcaVeiculoDTO atualizarMarcaVeiculoDTO){
        return ResponseEntity.ok().body(marcaVeiculoService.atualizarMarca(atualizarMarcaVeiculoDTO));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<MarcaVeiculoResponseDTO> desativarMarca(@PathVariable Long id){
        return ResponseEntity.ok().body(marcaVeiculoService.desativarMarca(id));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<MarcaVeiculoResponseDTO> ativarMarca(@PathVariable Long id){
        return ResponseEntity.ok().body(marcaVeiculoService.ativarMarca(id));
    }

    @GetMapping("/buscar/{marca}")
    public ResponseEntity<Page<MarcaVeiculoResponseDTO>> buscarMarca(@PathVariable String marca,
            @PageableDefault(page = 0, size = 20, sort = "nomeMarca")Pageable pageable){
        return ResponseEntity.ok().body(marcaVeiculoService.buscarMarca(marca, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<MarcaVeiculoResponseDTO>> buscarTodas(
            @PageableDefault(page = 0, size = 20, sort = "nomeMarca")Pageable pageable){
        return ResponseEntity.ok().body(marcaVeiculoService.buscarTodasMarcas(pageable));
    }

    @GetMapping("/buscar/ativa")
    public ResponseEntity<Page<MarcaVeiculoResponseDTO>> buscarMarcasAtivas(
            @PageableDefault(page = 0, size = 20, sort = "nomeMarca")Pageable pageable){
        return ResponseEntity.ok().body(marcaVeiculoService.buscarMarcasAtivas(pageable));
    }

    @GetMapping("/buscar/inativa")
    public ResponseEntity<Page<MarcaVeiculoResponseDTO>> buscarMarcasInativas(
            @PageableDefault(page = 0, size = 20, sort = "nomeMarca")Pageable pageable){
        return ResponseEntity.ok().body(marcaVeiculoService.buscarMarcasInativas(pageable));
    }
}
