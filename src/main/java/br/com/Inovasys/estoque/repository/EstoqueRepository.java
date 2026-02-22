package br.com.Inovasys.estoque.repository;

import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.estoque.entity.Estoque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Boolean existsByDescricaoAndEmpresa(String descricao, Empresa empresa);

    Boolean existsByCodigoAndEmpresa(String codigo, Empresa empresa);

    Boolean existsByDescricaoAndEmpresaAndIdNot(String descricao, Empresa empresa, Long id);

    Boolean existsByCodigoAndEmpresaAndIdNot(String codigo, Empresa empresa, Long id);

    Optional<Estoque> findByIdAndEmpresa(Long id, Empresa empresa);

    Optional<Estoque> findByCodigoAndEmpresa(String codigoItem, Empresa empresa);

    Page<Estoque> findByDescricaoContainingIgnoreCaseAndEmpresa(String nome, Empresa empresa, Pageable pageable);

    Page<Estoque> findByAtivoAndEmpresa(boolean b, Empresa empresa, Pageable pageable);

    Page<Estoque> findByEmpresa(Empresa empresa, Pageable pageable);
}
