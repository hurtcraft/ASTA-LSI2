package client.asta_lsi2.repository;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.EvaluationEcole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationEcoleRepository extends JpaRepository<EvaluationEcole, Long> {
    Optional<EvaluationEcole> findByApprenti(Apprenti apprenti);
    void deleteByApprenti(Apprenti apprenti);
}
