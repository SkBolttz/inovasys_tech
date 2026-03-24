package br.com.Inovasys.modulos.gestaoOficina.empresa.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCnpj(String cnpj);

    boolean existsByEmail(String email);
    Empresa findByCnpj(String cnpj);
}
