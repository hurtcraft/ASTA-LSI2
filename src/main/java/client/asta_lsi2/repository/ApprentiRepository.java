package client.asta_lsi2.repository;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface ApprentiRepository extends JpaRepository<Apprenti, Long> {


	List<Apprenti> findByAnneeAcademiqueDebut(Year anneeAcademiqueDebut);
	List<Apprenti> findByProgramme(Programme programme);
	Optional<Apprenti> findByApprentiId(Long apprentiId);
	boolean existsByApprentiEmail(String email);
	Optional<Apprenti> findByApprentiEmail(String username);
	List<Apprenti> findApprentisByApprentiName(String name);
	List<Apprenti> findByApprentiNameStartingWithIgnoreCase(String prefix);
	List<Apprenti> findApprentisByEntrepriseRaisonSociale(String entrepriseRaisonSociale);
	List<Apprenti> findByEntrepriseRaisonSocialeStartingWithIgnoreCase(String prefix);
}
