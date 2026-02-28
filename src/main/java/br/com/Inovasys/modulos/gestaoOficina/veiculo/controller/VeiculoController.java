package br.com.Inovasys.modulos.gestaoOficina.veiculo.controller;

import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.AtualizarVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.CadastrarVeiculoDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.VeiculoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<VeiculoResponseDTO> cadastrarVeiculo(
            @RequestBody CadastrarVeiculoDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(veiculoService.cadastrarVeiculo(dto));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<VeiculoResponseDTO> atualizarVeiculo(
            @RequestBody AtualizarVeiculoDTO dto) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.atualizarVeiculo(dto));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<VeiculoResponseDTO> ativarVeiculo(
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.ativarVeiculo(id));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<VeiculoResponseDTO> desativarVeiculo(
            @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.desativarVeiculo(id));
    }

    @GetMapping("/buscar/{placa}")
    public ResponseEntity<VeiculoResponseDTO> buscarVeiculo(
            @PathVariable String placa) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.buscarVeiculoPorPlaca(placa));
    }

    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<Page<VeiculoResponseDTO>> buscarVeiculoPorNome(
            @PathVariable String nome,
            @PageableDefault(page = 0, size = 20, sort = "placa") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.buscarVeiculosPorNomeCliente(nome, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<VeiculoResponseDTO>> buscarTodosVeiculos(
            @PageableDefault(page = 0, size = 20, sort = "placa") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.buscarTodos(pageable));
    }

    @GetMapping("/buscar/ativo")
    public ResponseEntity<Page<VeiculoResponseDTO>> buscarTodosVeiculosAtivos(
            @PageableDefault(page = 0, size = 20, sort = "placa") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.buscarVeiculosAtivos(pageable));
    }

    @GetMapping("/buscar/inativo")
    public ResponseEntity<Page<VeiculoResponseDTO>> buscarTodosVeiculosInativos(
            @PageableDefault(page = 0, size = 20, sort = "placa") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(veiculoService.buscarVeiculosInativos(pageable));
    }
}
