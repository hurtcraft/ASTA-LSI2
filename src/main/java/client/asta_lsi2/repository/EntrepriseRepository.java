package client.asta_lsi2.repository;

import client.asta_lsi2.models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
	Optional<Entreprise> findByRaisonSociale(String raisonSociale);
	boolean existsByRaisonSociale(String raisonSociale);
}
