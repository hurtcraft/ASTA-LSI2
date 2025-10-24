package client.asta_lsi2.service;

import client.asta_lsi2.models.AnneeAcademiqueCourante;
import client.asta_lsi2.repository.AnneeAcademiqueCouranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Service
public class AnneeAcademiqueCouranteService {
    
    private final AnneeAcademiqueCouranteRepository repository;
    
    public AnneeAcademiqueCouranteService(AnneeAcademiqueCouranteRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Obtient l'année académique courante
     * Si aucune n'existe, en crée une avec l'année courante
     */
    @Transactional
    public AnneeAcademiqueCourante getAnneeAcademiqueCourante() {
        Optional<AnneeAcademiqueCourante> anneeOpt = Optional.ofNullable(repository.findFirstByOrderByIdAsc());
        
        if (anneeOpt.isEmpty()) {
            // Première utilisation : créer l'année académique courante
            Year anneeCourante = Year.now();
            AnneeAcademiqueCourante nouvelleAnnee = new AnneeAcademiqueCourante(anneeCourante);
            return repository.save(nouvelleAnnee);
        }
        
        return anneeOpt.get();
    }
    
    /**
     * Passe à l'année académique suivante
     */
    @Transactional
    public AnneeAcademiqueCourante passerAnneeSuivante() {
        AnneeAcademiqueCourante anneeCourante = getAnneeAcademiqueCourante();
        anneeCourante.passerAnneeSuivante();
        return repository.save(anneeCourante);
    }
    
    /**
     * Obtient l'année de début de l'année académique courante
     */
    public Year getAnneeDebut() {
        return getAnneeAcademiqueCourante().getAnneeDebut();
    }
    
    /**
     * Obtient l'année de fin de l'année académique courante
     */
    public Year getAnneeFin() {
        return getAnneeAcademiqueCourante().getAnneeFin();
    }
    
    /**
     * Obtient l'année académique courante sous forme de string
     */
    public String getAnneeAcademiqueString() {
        return getAnneeAcademiqueCourante().getAnneeAcademiqueString();
    }
}
