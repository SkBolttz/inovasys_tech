package br.com.Inovasys.modulos.gestaoOficina.servicos.repository;

import aj.org.objectweb.asm.commons.Remapper;
import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.servicos.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Page<Servico> findByEmpresa(Empresa empresa, Pageable pageable);

    Page<Servico> findByDescricaoContainingIgnoreCaseAndEmpresa(String descricao, Empresa empresa, Pageable pageable);

    Page<Servico> findByEmpresaAndAtivo(Empresa empresa, boolean b, Pageable pageable);

    boolean existsByDescricaoIgnoreCaseAndEmpresa(String descricao, Empresa empresa);

    Optional<Servico> findByIdAndEmpresa(Long idServico, Empresa empresa);
}
