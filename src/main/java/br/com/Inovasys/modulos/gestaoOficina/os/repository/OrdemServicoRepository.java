package br.com.Inovasys.modulos.gestaoOficina.os.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import br.com.Inovasys.modulos.gestaoOficina.funcionarios.entity.Funcionario;
import br.com.Inovasys.modulos.gestaoOficina.os.entity.OrdemServico;
import br.com.Inovasys.modulos.gestaoOficina.os.enuns.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    Optional<OrdemServico> findByIdAndEmpresa(Long osId, Empresa empresa);

    Page<OrdemServico> findByEmpresa(Empresa empresa, Pageable pageable);

    Page<OrdemServico> findByEmpresaAndStatus(Empresa empresa, Status status, Pageable pageable);

    Page<OrdemServico> findByEmpresaAndFuncionarioResponsavel(Empresa empresa, Funcionario funcionario, Pageable pageable);

    Page<OrdemServico> findByEmpresaAndFuncionarioResponsavelAndStatus(Empresa empresa, Funcionario funcionario, Status status, Pageable pageable);
}
