package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import br.com.Inovasys.modulos.gestaoOficina.cliente.dto.ClienteResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.empresa.dto.empresa.EmpresaResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.dto.FuncionarioResponseDTO;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.dto.VeiculoResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrdemServicoResponseDTO(
        Long id,
        String numero,
        EmpresaResponseDTO empresa,
        ClienteResponseDTO cliente,
        VeiculoResponseDTO veiculo,
        FuncionarioResponseDTO funcionarioResponsavel,
        LocalDateTime dataAbertura,
        LocalDateTime dataConclusao,
        String status,
        Integer quilometragemEntrada,
        Integer quilometragemSaida,
        String descricaoProblema,
        String diagnostico,
        String observacoes,
        BigDecimal valorServicos,
        BigDecimal valorProdutos,
        BigDecimal desconto,
        BigDecimal valorTotal,
        String formaPagamento,
        Integer parcelas,
        Integer garantiaDias,
        Boolean ativo,
        List<ItemServicoOSResponseDTO> servicos,
        List<ItemEstoqueOSResponseDTO> produtos
) {
}
