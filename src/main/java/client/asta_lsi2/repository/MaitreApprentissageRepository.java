package client.asta_lsi2.repository;

import client.asta_lsi2.models.MaitreApprentissage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaitreApprentissageRepository extends JpaRepository<MaitreApprentissage, Long> {
    Optional<MaitreApprentissage> findByMaNom(String nom);

    Optional<MaitreApprentissage> findByMaEmail(String email);
    boolean existsByMaEmail(String email);
}