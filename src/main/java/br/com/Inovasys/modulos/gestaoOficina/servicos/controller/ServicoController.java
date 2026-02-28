package br.com.Inovasys.modulos.gestaoOficina.servicos.controller;


import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.AtualizarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.CadastrarServicoDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.dto.ServicoResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.servicos.serivce.ServicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servico")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ServicoResponseDTO> cadastrarServico(
            @RequestBody @Valid CadastrarServicoDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicoService.cadastrarServico(dto));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ServicoResponseDTO> atualizarServico(
            @RequestBody @Valid AtualizarServicoDTO dto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(servicoService.atualizarServico(dto));
    }

    @PutMapping("/ativar/{idServico}")
    public ResponseEntity<ServicoResponseDTO> ativarServico(
            @PathVariable Long idServico) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(servicoService.ativarServico(idServico));
    }

    @PutMapping("/desativar/{idServico}")
    public ResponseEntity<ServicoResponseDTO> desativarServico(
            @PathVariable Long idServico) {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(servicoService.desativarServico(idServico));
    }

    @GetMapping("/buscar/{descricao}")
    public ResponseEntity<Page<ServicoResponseDTO>> buscarServicoPorDescricao(
            @PathVariable String descricao,
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(servicoService.buscarPorDescricao(descricao, pageable));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ServicoResponseDTO>> buscarTodosServicos(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(servicoService.buscarTodos(pageable));
    }

    @GetMapping("/buscar/ativos")
    public ResponseEntity<Page<ServicoResponseDTO>> buscarAtivos(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(servicoService.buscarAtivos(pageable));
    }

    @GetMapping("/buscar/inativos")
    public ResponseEntity<Page<ServicoResponseDTO>> buscarInativos(
            @PageableDefault(page = 0, size = 20, sort = "descricao") Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(servicoService.buscarInativos(pageable));
    }
}
