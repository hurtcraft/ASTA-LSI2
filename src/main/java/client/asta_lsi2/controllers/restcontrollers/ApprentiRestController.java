package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/apprenti")
@Tag(name = "Apprenti", description = "Gestion des apprentis")

public class ApprentiRestController {

    private final ApprentiService apprentiService;
    public ApprentiRestController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @Operation(summary = "Récupère un apprenti par email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apprenti trouvé"),
            @ApiResponse(responseCode = "404", description = "Apprenti non trouvé")
    })
    @GetMapping("/getApprentiByEmail/{email}")
    public ResponseEntity<Apprenti> getApprentiByEmail(@Parameter(description = "Email de l'apprenti")@PathVariable String email) {
        return apprentiService.findApprentiByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Récupère tous les apprentis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des apprentis récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Apprenti>> getAllApprentis() {
        List<Apprenti> apprentis = apprentiService.findAll();
        return ResponseEntity.ok(apprentis);
    }

    @Operation(summary = "Récupère un apprenti par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apprenti trouvé"),
            @ApiResponse(responseCode = "404", description = "Apprenti non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Apprenti> getApprentiById(@Parameter(description = "ID de l'apprenti") @PathVariable Long id) {
        return apprentiService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crée un nouvel apprenti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Apprenti créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Email déjà utilisé")
    })
    @PostMapping("/create")
    public ResponseEntity<Apprenti> createApprenti(@RequestBody Apprenti apprenti) {
        if (apprentiService.apprentiExistsByEmail(apprenti.getApprentiEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        apprentiService.save(apprenti);
        return ResponseEntity.status(HttpStatus.CREATED).body(apprenti);
    }

    @Operation(summary = "Met à jour un apprenti existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apprenti mis à jour"),
            @ApiResponse(responseCode = "404", description = "Apprenti non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Apprenti> updateApprenti(@Parameter(description = "ID de l'apprenti") @PathVariable Long id, @RequestBody Apprenti apprenti) {
        return apprentiService.findById(id)
                .map(existingApprenti -> {
                    apprenti.setApprentiId(id);
                    apprentiService.save(apprenti);
                    return ResponseEntity.ok(apprenti);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Supprime un apprenti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apprenti supprimé"),
            @ApiResponse(responseCode = "404", description = "Apprenti non trouvé")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteApprenti(@Parameter(description = "ID de l'apprenti") @PathVariable Long id) {
        if (apprentiService.findById(id).isPresent()) {
            apprentiService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
