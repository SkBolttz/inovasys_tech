package br.com.Inovasys.modulos.gestaoOficina.veiculo.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.MarcaVeiculo;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.ModeloVeiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ModeloVeiculoRepository extends JpaRepository<ModeloVeiculo, Long> {
    Page<ModeloVeiculo> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<ModeloVeiculo> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    boolean existsByModeloVeiculoAndEmpresa(String nomeModelo, Empresa empresa);

    boolean existsByModeloVeiculoAndEmpresaAndIdNot(String nomeModelo, Empresa empresa, Long id);

    Optional<ModeloVeiculo> findByIdAndEmpresa(Long id, Empresa empresa);

    boolean findByMarcaVeiculoAndEmpresa(MarcaVeiculo marcaVeiculo, Empresa empresa);

    Page<ModeloVeiculo> findByModeloVeiculoContainingIgnoreCaseAndEmpresa(String nomeModelo, Empresa empresa, Pageable pageable);
}
