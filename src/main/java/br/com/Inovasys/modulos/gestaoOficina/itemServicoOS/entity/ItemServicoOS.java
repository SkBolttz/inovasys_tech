package br.com.Inovasys.modulos.gestaoOficina.itemServicoOS.entity;

import java.math.BigDecimal;

import br.com.Inovasys.modulos.gestaoOficina.os.entity.OrdemServico;
import br.com.Inovasys.modulos.gestaoOficina.servicos.entity.Servico;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "item_servico_os")
@Data
public class ItemServicoOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrdemServico ordemServico;

    @ManyToOne
    private Servico servico;

    private BigDecimal valorUnitario;
    private Integer quantidade;
    private BigDecimal valorTotal;
}

