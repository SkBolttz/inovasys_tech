package br.com.Inovasys.modulos.gestaoOficina.os.controller;

import br.com.Inovasys.modulos.gestaoOficina.os.dto.*;
import br.com.Inovasys.modulos.gestaoOficina.os.enuns.Status;
import br.com.Inovasys.modulos.gestaoOficina.os.service.OrdemServicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController(OrdemServicoService ordemServicoService){
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<OrdemServicoResponseDTO> cadastrarNovaOS(@RequestBody CriarOrdemServicoDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordemServicoService.criarOrdemServico(dto));
    }

    @PutMapping("/adicionar-servico")
    public ResponseEntity<OrdemServicoResponseDTO> adicionarServicoOs(@RequestBody AdicionarServicoOSDTO dto){
        return ResponseEntity.ok(ordemServicoService.adicionarServicoOS(dto));
    }

    @PutMapping("/adicionar-produto")
    public ResponseEntity<OrdemServicoResponseDTO> adicionarProdutoOS(@RequestBody AdicionarProdutoOSDTO dto){
        return ResponseEntity.ok(ordemServicoService.adicionarProdutoOS(dto));
    }

    @PutMapping("/remover/OS/{osId}/servico/{itemServicoId}")
    public ResponseEntity<OrdemServicoResponseDTO> removerServicoOS(@PathVariable Long osId,@PathVariable Long itemServicoId){
        return ResponseEntity.ok(ordemServicoService.removerServicoOS(osId, itemServicoId));
    }

    @PutMapping("/remover/OS/{osId}/produto/{itemEstoqueId}")
    public ResponseEntity<OrdemServicoResponseDTO> removerProdutoOS(@PathVariable Long osId,@PathVariable Long itemEstoqueId){
        return ResponseEntity.ok(ordemServicoService.removerProdutoOS(osId, itemEstoqueId));
    }

    @PutMapping("/iniciar/{osId}")
    public ResponseEntity<OrdemServicoResponseDTO> iniciarOS(@PathVariable Long osId) {
        return ResponseEntity.ok(ordemServicoService.iniciarOS(osId));
    }

    @PutMapping("/aguardando-pecas/{osId}")
    public ResponseEntity<OrdemServicoResponseDTO> aguardarPecasOS(@PathVariable Long osId) {
        return ResponseEntity.ok(ordemServicoService.aguardarPecaOS(osId));
    }

    @GetMapping("/listar/OS/{osId}")
    public ResponseEntity<OrdemServicoResponseDTO> buscarOSPorId(@PathVariable Long osId) {
        return ResponseEntity.ok(ordemServicoService.listarOrdensDeServicoPorId(osId));
    }

    @PutMapping("/finalizar")
    public ResponseEntity<OrdemServicoResponseDTO> finalizarOS(@RequestBody FinalizarOsDTO finalizarOsDTO){
        return ResponseEntity.ok(ordemServicoService.finalizarOS(finalizarOsDTO));
    }

    @PutMapping("/cancelar/{osId}")
    public ResponseEntity<OrdemServicoResponseDTO> cancelarOS(@PathVariable Long osId) {
        return ResponseEntity.ok(ordemServicoService.cancelarOS(osId));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<OrdemServicoResponseDTO>> listarTodasOrdens(
            @PageableDefault(page = 0, size = 20, sort = "numero") Pageable pageable) {
        return ResponseEntity.ok(ordemServicoService.listarOrdensDeServico(pageable));
    }

    @GetMapping("/listar/status/{status}")
    public ResponseEntity<Page<OrdemServicoResponseDTO>> listarOrdensPorStatus(@PathVariable Status status,
            @PageableDefault(page = 0, size = 20, sort = "numero") Pageable pageable) {
        return ResponseEntity.ok(
                ordemServicoService.listarOrdensDeServicoPorStatus(status, pageable)
        );
    }

    @GetMapping("/listar/funcionario/{cpfFuncionario}")
    public ResponseEntity<Page<OrdemServicoResponseDTO>> listarOsFuncionario(@PathVariable String cpfFuncionario,
            @RequestParam(required = false) Status status, @PageableDefault(page = 0, size = 20, sort = "numero")
            Pageable pageable) {
        return ResponseEntity.ok(
                ordemServicoService.listarOsDoFuncionario(cpfFuncionario, status, pageable)
        );
    }
}

