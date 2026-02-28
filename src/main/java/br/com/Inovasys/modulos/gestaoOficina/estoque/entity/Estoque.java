package br.com.Inovasys.modulos.gestaoOficina.estoque.entity;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.estoque.Enum.UnidadeMedida;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "estoques")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificação do item
    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(length = 50)
    private String codigo; // código interno / barras / SKU

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoCompra;
    // Valores
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    // Controle de estoque
    @Column(nullable = false)
    private Integer estoqueAtual;

    @Column(nullable = false)
    private Integer estoqueMinimo;

    @Column(nullable = false)
    private Integer estoqueMaximo;

    // Informações adicionais
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private UnidadeMedida unidadeMedida;

    private LocalDate ultimaReposicao;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    // Controle do sistema
    @Column(nullable = false)
    private Boolean ativo;
}