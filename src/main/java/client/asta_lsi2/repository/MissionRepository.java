package client.asta_lsi2.repository;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findMissionsByMotCleContainingIgnoreCase(String motCle);
    List<Mission> findByApprenti(Apprenti apprenti);
}