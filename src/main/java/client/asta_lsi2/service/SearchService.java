package client.asta_lsi2.service;


import client.asta_lsi2.models.Apprenti;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final ApprentiService apprentiService;
    private final MissionService missionService;
    public SearchService(ApprentiService apprentiService, MissionService missionService) {
        this.apprentiService = apprentiService;
        this.missionService = missionService;
    }

    public List<Apprenti> findByApprentiName(String apprentiName) {
        // Perform case-insensitive "starts with" search so typing "so" matches "Sopra", "Sopra Steria", etc.
        return apprentiService.findByApprentiNameStartingWithIgnoreCase(apprentiName);
    }
    public List<Apprenti> findApprentiByMissionMotCleContaining(String motCle) {
        return missionService.findApprentisByMotCleContainingIgnoreCase(motCle);
    }
    public List<Apprenti> findByEntrepriseName(String entrepriseName) {
        // Case-insensitive prefix match on entreprise raison sociale
        return apprentiService.findByEntrepriseRaisonSocialeStartingWithIgnoreCase(entrepriseName);
    }
    public List<Apprenti> findByAnne(int anne) {
        return apprentiService.findByAnne(anne);
    }

        public List<Apprenti> findAllApprentis() {
            return apprentiService.findAll();
        }
}
