package client.asta_lsi2.controllers.restcontrollers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.MaitreApprentissageService;
import client.asta_lsi2.service.ProgrammeService;
import client.asta_lsi2.service.TuteurEnseingnantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterRestController {

    private final ApprentiService apprentiService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final ProgrammeService programmeService;
    private final TuteurEnseingnantService tuteurEnseingnantService;

    public RegisterRestController(ApprentiService apprentiService,
                                  MaitreApprentissageService maitreApprentissageService,
                                  ProgrammeService programmeService,
                                  TuteurEnseingnantService tuteurEnseingnantService) {
        this.apprentiService = apprentiService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.programmeService = programmeService;
        this.tuteurEnseingnantService = tuteurEnseingnantService;
    }

    @PostMapping("/apprenti")
    public ResponseEntity<?> registerApprenti(@RequestBody Apprenti apprenti) {
        apprentiService.save(apprenti);
        return ResponseEntity.ok("Apprenti enregistré avec succès");
    }

    @PostMapping("/maitre")
    public ResponseEntity<?> registerMaitre(@RequestBody MaitreApprentissage maitreApprentissage) {
        maitreApprentissageService.save(maitreApprentissage);
        return ResponseEntity.ok("Maître d'apprentissage enregistré avec succès");
    }

    @PostMapping("/tuteurEnseignant")
    public ResponseEntity<?> registerTuteur(@RequestBody TuteurEnseignant tuteurEnseignant) {
        tuteurEnseingnantService.save(tuteurEnseignant);
        return ResponseEntity.ok("Tuteur enseignant enregistré avec succès");
    }

    @GetMapping("/programmes")
    public ResponseEntity<?> getProgrammes() {
        return ResponseEntity.ok(programmeService.findAll());
    }
}