package br.com.Inovasys.funcionarios.repository;

import aj.org.objectweb.asm.commons.Remapper;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.funcionarios.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByCpfAndEmpresa(String cpf, Empresa empresa);

    boolean existsByEmailAndEmpresa(String email, Empresa empresa);
    boolean existsByCpfAndEmpresa(String cpf, Empresa empresa);
    boolean existsByTelefoneAndEmpresa(String telefone, Empresa empresa);

    boolean existsByEmailAndEmpresaAndIdNot(String email, Empresa empresa, Long id);
    boolean existsByTelefoneAndEmpresaAndIdNot(String telefone, Empresa empresa, Long id);

    Page<Funcionario> findByNomeContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);

    Page<Funcionario> findByEmpresa(Empresa empresa, Pageable pageable);

    Page<Funcionario> findByAtivoAndEmpresa(Boolean ativo, Empresa empresa, Pageable pageable);

    Optional<Funcionario> existsByCpf(String cpf);
}
