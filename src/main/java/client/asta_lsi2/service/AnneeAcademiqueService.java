package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.ApprentiArchive;
import client.asta_lsi2.models.Programme;
import client.asta_lsi2.repository.ApprentiRepository;
import client.asta_lsi2.repository.ApprentiArchiveRepository;
import client.asta_lsi2.repository.EvaluationEcoleRepository;
import client.asta_lsi2.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
public class AnneeAcademiqueService {
    
    private final ApprentiRepository apprentiRepository;
    private final ApprentiArchiveRepository apprentiArchiveRepository;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;
    private final EvaluationEcoleRepository evaluationEcoleRepository;
    private final MissionRepository missionRepository;
    
    public AnneeAcademiqueService(ApprentiRepository apprentiRepository, 
                                 ApprentiArchiveRepository apprentiArchiveRepository,
                                 AnneeAcademiqueCouranteService anneeAcademiqueCouranteService,
                                 EvaluationEcoleRepository evaluationEcoleRepository,
                                 MissionRepository missionRepository) {
        this.apprentiRepository = apprentiRepository;
        this.apprentiArchiveRepository = apprentiArchiveRepository;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
        this.evaluationEcoleRepository = evaluationEcoleRepository;
        this.missionRepository = missionRepository;
    }
    
    /**
     * Crée une nouvelle année académique en :
     * 1. Archivant les apprentis de I3
     * 2. Promouvant I1 vers I2 et I2 vers I3
     * 3. Mettant à jour les années académiques
     */
    @Transactional
    public void creerNouvelleAnneeAcademique() {
        // Obtenir l'année académique courante
        Year ancienneAnnee = anneeAcademiqueCouranteService.getAnneeDebut();
        
        // 1. Archiver les apprentis de I3
        List<Apprenti> apprentisI3 = apprentiRepository.findByProgramme(Programme.I3);
        for (Apprenti apprenti : apprentisI3) {
            // Supprimer d'abord les enregistrements liés pour éviter les contraintes de clé étrangère
            evaluationEcoleRepository.deleteByApprenti(apprenti);
            missionRepository.deleteAll(missionRepository.findByApprenti(apprenti));
            
            // Créer l'archive
            ApprentiArchive archive = new ApprentiArchive(apprenti, ancienneAnnee);
            apprentiArchiveRepository.save(archive);
            
            // Supprimer l'apprenti
            apprentiRepository.delete(apprenti);
        }
        
        // 2. Promouvoir les apprentis I2 vers I3
        List<Apprenti> apprentisI2 = apprentiRepository.findByProgramme(Programme.I2);
        for (Apprenti apprenti : apprentisI2) {
            apprenti.setProgramme(Programme.I3);
            // L'année académique sera mise à jour après l'incrémentation
            apprentiRepository.save(apprenti);
        }
        
        // 3. Promouvoir les apprentis I1 vers I2
        List<Apprenti> apprentisI1 = apprentiRepository.findByProgramme(Programme.I1);
        for (Apprenti apprenti : apprentisI1) {
            apprenti.setProgramme(Programme.I2);
            // L'année académique sera mise à jour après l'incrémentation
            apprentiRepository.save(apprenti);
        }
        
        // 4. Passer à l'année académique suivante
        anneeAcademiqueCouranteService.passerAnneeSuivante();
        
        // 5. Mettre à jour les années académiques de tous les apprentis
        Year nouvelleAnnee = anneeAcademiqueCouranteService.getAnneeDebut();
        List<Apprenti> tousApprentis = apprentiRepository.findAll();
        for (Apprenti apprenti : tousApprentis) {
            apprenti.setAnneeAcademiqueDebut(nouvelleAnnee);
            apprenti.setAnneeAcademiqueFin(nouvelleAnnee.plusYears(1));
            apprentiRepository.save(apprenti);
        }
    }
    
    /**
     * Obtient les statistiques de l'année académique courante
     */
    public AnneeAcademiqueStats getStatsAnneeCourante() {
        Year anneeCourante = anneeAcademiqueCouranteService.getAnneeDebut();
        
        long nbI1 = apprentiRepository.findByProgramme(Programme.I1).size();
        long nbI2 = apprentiRepository.findByProgramme(Programme.I2).size();
        long nbI3 = apprentiRepository.findByProgramme(Programme.I3).size();
        
        return new AnneeAcademiqueStats(anneeCourante, nbI1, nbI2, nbI3);
    }
    
    /**
     * Obtient les statistiques des archives pour une année donnée
     */
    public List<ApprentiArchive> getArchivesParAnnee(Year annee) {
        return apprentiArchiveRepository.findByAnneeArchivage(annee);
    }
    
    /**
     * Classe interne pour les statistiques
     */
    public static class AnneeAcademiqueStats {
        private final Year annee;
        private final long nbI1;
        private final long nbI2;
        private final long nbI3;
        
        public AnneeAcademiqueStats(Year annee, long nbI1, long nbI2, long nbI3) {
            this.annee = annee;
            this.nbI1 = nbI1;
            this.nbI2 = nbI2;
            this.nbI3 = nbI3;
        }
        
        public Year getAnnee() { return annee; }
        public long getNbI1() { return nbI1; }
        public long getNbI2() { return nbI2; }
        public long getNbI3() { return nbI3; }
        public long getTotal() { return nbI1 + nbI2 + nbI3; }
    }
}