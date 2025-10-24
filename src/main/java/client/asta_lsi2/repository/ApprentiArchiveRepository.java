package client.asta_lsi2.repository;

import client.asta_lsi2.models.ApprentiArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface ApprentiArchiveRepository extends JpaRepository<ApprentiArchive, Long> {
    
    // Trouver tous les apprentis archivés pour une année donnée
    List<ApprentiArchive> findByAnneeArchivage(Year anneeArchivage);
    
    // Trouver tous les apprentis archivés avec un programme final donné
    List<ApprentiArchive> findByProgrammeFinal(client.asta_lsi2.models.Programme programmeFinal);
    
    // Vérifier si un email existe dans les archives
    boolean existsByApprentiEmail(String email);
}
