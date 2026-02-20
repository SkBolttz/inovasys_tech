package br.com.Inovasys.empresa.controller;

import br.com.Inovasys.empresa.api.ReceitaWS;
import br.com.Inovasys.empresa.dto.empresa.CadastrarEmpresaDTO;
import br.com.Inovasys.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.empresa.dto.empresa.ReceitaEmpresaDTO;
import br.com.Inovasys.empresa.serivce.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final ReceitaWS receitaWS;

    public EmpresaController(EmpresaService empresaService, ReceitaWS receitaWS){
        this.empresaService = empresaService;
        this.receitaWS = receitaWS;
    }

    @PostMapping("/buscar/{cnpj}")
    public ResponseEntity<ReceitaEmpresaDTO> buscarEmpresaReceita(@PathVariable String cnpj){
        return ResponseEntity.ok().body(receitaWS.buscarCnpj(cnpj));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<EmpresaResponseDTO> cadastrarEmpresa(@RequestBody @Valid CadastrarEmpresaDTO cadastrarEmpresaDTO){
        return ResponseEntity.ok().body(empresaService.cadastrarEmpresa(cadastrarEmpresaDTO));
    }
}
