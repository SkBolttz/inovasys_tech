package br.com.Inovasys.modulos.gestaoOficina.os.dto;

import br.com.Inovasys.modulos.gestaoOficina.os.enuns.FormaPagamento;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CriarOrdemServicoDTO(
        Long idCliente,
        Long idFuncionario,
        Long idVeiculo,
        String descricaoProblema,
        Integer quilometragemEntrada,
        BigDecimal desconto,
        String observacoes,
        LocalDate prazoEntrega,

        // --- NOVOS CAMPOS PARA CHECKLIST ---
        Boolean temEstepe,
        Boolean temMacaco,
        Boolean temChaveRoda,
        Boolean temTriangulo,
        Boolean temExtintor,
        String nivelCombustivel,

        // --- LISTAS PARA ITENS E INSPEÇÃO VISUAL ---
        List<AvariaOSResponseDTO> avarias,
        List<AdicionarServicoOSDTO> servicos, // Se você já quiser enviar os itens na criação
        List<AdicionarProdutoOSDTO> produtos
) {
}