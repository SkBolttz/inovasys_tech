package br.com.Inovasys.veiculo.entity;

import br.com.Inovasys.empresa.entity.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "modelo_veiculo")
public class ModeloVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String modeloVeiculo;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    private Boolean ativo = true;
    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private MarcaVeiculo marcaVeiculo;
}
