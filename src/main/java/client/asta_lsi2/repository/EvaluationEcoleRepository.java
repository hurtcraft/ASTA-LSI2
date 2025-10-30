package client.asta_lsi2.repository;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.EvaluationEcole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EvaluationEcoleRepository extends JpaRepository<EvaluationEcole, Long> {
//    Optional<EvaluationEcole> findByApprenti(Apprenti apprenti);
//    void deleteByApprenti(Apprenti apprenti);

    @Query(
            value = "SELECT * FROM evaluation_ecole WHERE apprenti_id = :apprentiId",
            nativeQuery = true
    )
    Optional<EvaluationEcole> findByApprenti(@Param("apprentiId") Long apprentiId);

    @Query("DELETE FROM EvaluationEcole e WHERE e.apprenti = :apprenti")
    @Modifying
    @Transactional
    void deleteByApprenti(@Param("apprenti") Apprenti apprenti);
}
