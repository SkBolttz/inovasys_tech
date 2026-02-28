package br.com.Inovasys.veiculo.repository;

import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.veiculo.entity.MarcaVeiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MarcaVeiculoRepository extends JpaRepository<MarcaVeiculo, Long> {
    boolean existsByNomeMarcaAndEmpresa(String nomeMarca, Empresa empresa);

    boolean existsByNomeMarcaAndEmpresaAndIdNot(String nomeMarca, Empresa empresa, Long id);

    Optional<MarcaVeiculo> findByIdAndEmpresa(Long id, Empresa empresa);

    Page<MarcaVeiculo> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<MarcaVeiculo> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    Page<MarcaVeiculo> findByNomeMarcaContainingIgnoreCaseAndEmpresa(String nomeMarca, Empresa empresa, Pageable pageable);
}
