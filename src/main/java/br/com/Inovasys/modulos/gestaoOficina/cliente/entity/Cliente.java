package br.com.Inovasys.modulos.gestaoOficina.cliente.entity;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados pessoais
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpfCnpj;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    private LocalDate dataNascimento;

    // Controle
    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private Boolean ativo;

    // Dados da Empresa que o cliente está vinculado.
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}
