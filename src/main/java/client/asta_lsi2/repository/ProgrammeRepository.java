package client.asta_lsi2.repository;

import client.asta_lsi2.models.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    Optional<Programme> findByLabel(String label);
}
