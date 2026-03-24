package br.com.Inovasys.modulos.gestaoOficina.os.repository;

import br.com.Inovasys.modulos.gestaoOficina.os.entity.AvariaOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvariaOsRepository extends JpaRepository<AvariaOS, Long> {
}
