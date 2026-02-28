package br.com.Inovasys.modulos.gestaoOficina.empresa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String logradouro;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String numero;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String municipio;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve conter 2 letras maiúsculas")
    @Column(nullable = false, length = 2)
    private String uf;

    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
    @Column(nullable = false, length = 8)
    private String cep;
}
