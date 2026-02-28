package br.com.Inovasys.cliente.repository;

import br.com.Inovasys.cliente.entity.Cliente;
import br.com.Inovasys.empresa.entity.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCpfCnpjAndEmpresa(String cpfCnpj, Empresa empresa);

    boolean existsByEmailAndEmpresa(String email, Empresa empresa);

    Optional<Cliente> findByCpfCnpjAndEmpresa(String cpfCnpj, Empresa empresa);

    boolean existsByEmailAndEmpresaAndIdNot(String email, Empresa empresa, Long id);

    boolean existsByTelefoneAndEmpresaAndIdNot(String telefone, Empresa empresa, Long id);

    Page<Cliente> findByNomeContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);

    Page<Cliente> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<Cliente> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    Optional<Cliente> findByIdAndEmpresa(Long aLong, Empresa empresa);
}
