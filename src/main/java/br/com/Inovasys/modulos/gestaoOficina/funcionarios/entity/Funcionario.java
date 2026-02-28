package br.com.Inovasys.modulos.gestaoOficina.funcionarios.entity;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "funcionarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 100)
    @Email
    private String email;

    @Column(length = 20)
    private String telefone;

    @ManyToOne
    private Empresa empresa;

    private LocalDate dataNascimento;

    private LocalDate dataAdmissao;

    private Boolean ativo = true;
}

