package client.asta_lsi2.controllers.restcontrollers;

import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.service.MaitreApprentissageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/maitre")
@Tag(name = "Maitre d'apprentissage", description = "Gestion des maîtres d'apprentissage")
public class MaitreApprentissageRestController {

    private final MaitreApprentissageService maitreApprentissageService;

    public MaitreApprentissageRestController(MaitreApprentissageService maitreApprentissageService) {
        this.maitreApprentissageService = maitreApprentissageService;
    }

    @Operation(summary = "Lister tous les maîtres d'apprentissage")
    @GetMapping("/all")
    public ResponseEntity<List<MaitreApprentissage>> getAll() {
        return ResponseEntity.ok(maitreApprentissageService.findAll());
    }

    @Operation(summary = "Créer un maître d'apprentissage")
    @PostMapping
    public ResponseEntity<MaitreApprentissage> create(@RequestBody MaitreApprentissage ma) {
        maitreApprentissageService.save(ma);
        return ResponseEntity.created(URI.create("/api/maitre/" + ma.getMaId())).body(ma);
    }

    @Operation(summary = "Récupérer un maître d'apprentissage par id")
    @GetMapping("/{id}")
    public ResponseEntity<MaitreApprentissage> getById(@PathVariable Long id) {
        return maitreApprentissageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
