package br.com.Inovasys.veiculo.entity;

import br.com.Inovasys.cliente.entity.Cliente;
import br.com.Inovasys.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String placa;

    @ManyToOne
    @JoinColumn(name = "modelo_veiculo_id", nullable = false)
    private ModeloVeiculo modelo;

    @Column(length = 20)
    private String cor;

    @Column(length = 4)
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "tipo_veiculo_id", nullable = false)
    private TipoVeiculo tipo;

    @ManyToOne
    @JoinColumn(name = "tipo_combustivel_id", nullable = false)
    private TipoCombustivel combustivel;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    private Boolean ativo = true;
}
