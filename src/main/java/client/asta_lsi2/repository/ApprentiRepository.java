package client.asta_lsi2.repository;

import client.asta_lsi2.models.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprentiRepository extends JpaRepository<Apprenti, Long> {
    Optional<Apprenti> findByUsername(String username);
}