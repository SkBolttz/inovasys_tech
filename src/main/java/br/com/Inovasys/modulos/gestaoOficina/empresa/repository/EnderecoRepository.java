package br.com.Inovasys.modulos.gestaoOficina.empresa.repository;

import br.com.Inovasys.modulos.gestaoOficina.empresa.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
