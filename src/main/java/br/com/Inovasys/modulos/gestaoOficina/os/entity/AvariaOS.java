package br.com.Inovasys.modulos.gestaoOficina.os.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "os_avarias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvariaOS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "os_id", nullable = false)
    private OrdemServico ordemServico;

    // Coordenadas percentuais (0 a 100) para manter a posição
    // independente do tamanho da tela (Responsividade)
    private Double eixoX;
    private Double eixoY;

    @Column(length = 50)
    private String tipoDano; // Ex: "Risco", "Amassado", "Quebrado"

    @Column(length = 255)
    private String observacao;
}