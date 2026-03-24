package br.com.Inovasys.modulos.gestaoOficina.veiculo.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    boolean existsByPlacaAndEmpresa(String placa, Empresa empresa);

    Page<Veiculo> findByCliente_NomeContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);

    Page<Veiculo> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<Veiculo> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    Optional<Veiculo> findByPlacaAndEmpresa(String placa, Empresa empresa);

    Optional<Veiculo> findByIdAndEmpresa(Long id, Empresa empresa);

    Page<Veiculo> findByClienteIdAndEmpresa(Long idCliente, Empresa empresa, Pageable pageable);
}
