package client.asta_lsi2.repository;

import client.asta_lsi2.models.AnneeAcademiqueCourante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeAcademiqueCouranteRepository extends JpaRepository<AnneeAcademiqueCourante, Long> {
    
    // Trouver l'année académique courante (il ne devrait y en avoir qu'une)
    AnneeAcademiqueCourante findFirstByOrderByIdAsc();
}
