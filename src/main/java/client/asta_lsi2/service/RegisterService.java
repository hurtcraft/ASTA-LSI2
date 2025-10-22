package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.repository.ApprentiRepository;
import client.asta_lsi2.repository.MaitreApprentissageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {


    public final ApprentiService apprentiService;
    public final MaitreApprentissageService maitreApprentissageService;




    public RegisterService(ApprentiService apprentiService,MaitreApprentissageService maitreApprentissageService) {
        this.apprentiService= apprentiService;
        this.maitreApprentissageService=maitreApprentissageService;
    }

    public ResponseEntity<String> registerApprenti(Apprenti apprenti) {
        if (apprentiService.apprentiExistsByEmail(apprenti.getApprentiEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Apprenti déjà existant");
        }

        apprentiService.save(apprenti);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Apprenti enregistré avec succès");
    }



    public ResponseEntity<String> registerMaitre(MaitreApprentissage maitre) {

        if (maitreApprentissageService.maitreExistsByEmail(maitre.getMaEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Maitre d'apprentissage déjà existant");
        }

        maitreApprentissageService.save(maitre);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Maitre d'apprentissage enregistré avec succès");
    }
}
