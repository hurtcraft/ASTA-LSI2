package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.TuteurEnseingnantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tuteurEnseignant")
public class TuteurEnseignantRestController {
    private final TuteurEnseingnantService tuteurEnseignantService;

    public TuteurEnseignantRestController(TuteurEnseingnantService tuteurEnseingnantService) {
        this.tuteurEnseignantService = tuteurEnseingnantService;
    }
    @PostMapping("/save")
    public ResponseEntity<TuteurEnseignant> saveTuteur(@RequestBody TuteurEnseignant tuteur) {
        tuteurEnseignantService.save(tuteur);
        return ResponseEntity.ok(tuteur);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<TuteurEnseignant> getTuteurByEmail(@PathVariable String email) {
        Optional<TuteurEnseignant> tuteur = tuteurEnseignantService.getTuteurEnseignantByEmail(email);
        return tuteur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
