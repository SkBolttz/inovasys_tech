package br.com.Inovasys.auth.repository;

import br.com.Inovasys.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByCpf(String cpf);

    Optional<Boolean> existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);
}
