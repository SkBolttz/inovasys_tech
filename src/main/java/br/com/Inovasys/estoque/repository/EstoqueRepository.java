package br.com.Inovasys.estoque.repository;

import br.com.Inovasys.empresa.entity.Empresa;
import br.com.Inovasys.estoque.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Boolean existsDescricaoAndEmpresa(String descricao, Empresa empresa);

    Boolean existsByCodigoAndEmpresa(String codigo, Empresa empresa);

    Boolean existsByDescricaoAndEmpresaAndIdNot(String descricao, Empresa empresa, Long id);

    Boolean existsByCodigoAndEmpresaAndIdNot(String codigo, Empresa empresa, Long id);
}
