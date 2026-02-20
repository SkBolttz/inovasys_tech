package br.com.Inovasys.empresa.entity;

import br.com.Inovasys.auth.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(
        name = "empresas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = true, length = 18)
    private String cnpj;

    // 🔹 Dados cadastrais
    @PastOrPresent
    @Column(name = "data_abertura", nullable = false)
    private LocalDate abertura;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String situacao;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nome;

    @Size(max = 50)
    @Column(length = 50)
    private String porte;

    @Size(max = 50)
    @Column(length = 50)
    private String tipo;

    @Size(max = 100)
    @Column(name = "natureza_juridica", length = 100)
    private String naturezaJuridica;

    @Size(max = 200)
    @Column(name = "atividade_principal", length = 200)
    private String atividadePrincipal;

    @Size(max = 200)
    @Column(name = "atividade_secundaria", length = 200)
    private String atividadadeSecundaria;

    // 🔹 Contato
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    @Column(length = 11)
    private String telefone;

    // 🔹 Relacionamento 1:1 com Endereco
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    // 🔹 Relacionamento 1:N com usuários
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Users> usuarios;
}
