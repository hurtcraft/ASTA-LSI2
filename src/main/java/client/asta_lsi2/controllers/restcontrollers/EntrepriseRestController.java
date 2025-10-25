package client.asta_lsi2.controllers.restcontrollers;

import client.asta_lsi2.models.Entreprise;
import client.asta_lsi2.service.EntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/entreprises")
@Tag(name = "Entreprise", description = "Gestion des entreprises")
public class EntrepriseRestController {

    private final EntrepriseService entrepriseService;

    public EntrepriseRestController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Operation(summary = "Lister toutes les entreprises")
    @GetMapping("/all")
    public ResponseEntity<List<Entreprise>> getAll() {
        return ResponseEntity.ok(entrepriseService.findAll());
    }

    @Operation(summary = "Créer une entreprise")
    @PostMapping
    public ResponseEntity<Entreprise> create(@RequestBody Entreprise entreprise) {
        Entreprise saved = entrepriseService.save(entreprise);
        return ResponseEntity.created(URI.create("/api/entreprises/" + saved.getEntrepriseId())).body(saved);
    }

    @Operation(summary = "Récupérer une entreprise par id")
    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> getById(@PathVariable Long id) {
        return entrepriseService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
