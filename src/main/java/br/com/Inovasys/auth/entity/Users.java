package br.com.Inovasys.auth.entity;

import br.com.Inovasys.auth.role.PerfilUsuario;
import br.com.Inovasys.auth.role.StatusUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"empresa_id", "email"}),
                @UniqueConstraint(columnNames = {"empresa_id", "cpf"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Multi-tenant obrigatório
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    // 🔹 Dados pessoais
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 100)
    @Column(length = 100)
    private String sobrenome;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Column(nullable = false, length = 11)
    private String cpf;

    @Email
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String email;

    @Pattern(regexp = "\\d{10,11}")
    @Column(length = 11)
    private String telefone;

    @Past
    private LocalDate dataNascimento;

    @NotBlank
    @Column(nullable = false)
    private String senhaHash;

    // 🔹 Status controlado por enum (evita conflito de boolean)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PerfilUsuario perfilUsuario;

    // 🔹 Controle de login
    @Column(nullable = false)
    private Integer tentativasLogin = 0;

    private LocalDateTime ultimoLogin;

    private Boolean primeiroLogin = true;

    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfilUsuario.name().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return cpf;
    }
}
