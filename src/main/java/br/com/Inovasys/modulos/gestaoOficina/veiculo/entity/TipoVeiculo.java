package br.com.Inovasys.modulos.gestaoOficina.veiculo.entity;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tipo_veiculo")
public class TipoVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String tipoVeiculo;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    private Boolean ativo = true;
}
