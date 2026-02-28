package br.com.Inovasys.veiculo.repository;

import aj.org.objectweb.asm.commons.Remapper;
import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.veiculo.entity.TipoVeiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Long> {
    Page<TipoVeiculo> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<TipoVeiculo> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    boolean existsByTipoVeiculoAndEmpresa(String nome, Empresa empresa);

    boolean existsByTipoVeiculoAndEmpresaAndIdNot(String nome, Empresa empresa, Long id);

    Optional<TipoVeiculo> findByIdAndEmpresa(Long id, Empresa empresa);

    Page<TipoVeiculo> findByTipoVeiculoContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);
}
