package br.com.Inovasys.modulos.gestaoOficina.veiculo.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.veiculo.entity.TipoCombustivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoCombustivelRepository extends JpaRepository<TipoCombustivel, Long> {
    Page<TipoCombustivel> findAllByEmpresa(Empresa empresa, Pageable pageable);

    Page<TipoCombustivel> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    boolean existsByTipoCombustivelAndEmpresa(String nome, Empresa empresa);

    boolean existsByTipoCombustivelAndEmpresaAndIdNot(String nome, Empresa empresa, Long id);

    Optional<TipoCombustivel> findByIdAndEmpresa(Long id, Empresa empresa);

    Optional<TipoCombustivel> findByTipoCombustivelAndEmpresa(String nome, Empresa empresa);

    Page<TipoCombustivel> findByTipoCombustivelContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);
}
