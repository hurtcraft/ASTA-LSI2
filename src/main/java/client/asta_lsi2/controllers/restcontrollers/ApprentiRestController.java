package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

    @DeleteMapping("/deleteApprenti/{id}")
    public ResponseEntity<Void> deleteApprentiById(@PathVariable Long id) {
        Optional<Apprenti> apprentiOpt = apprentiService.findById(id);
        if (apprentiOpt.isPresent()) {
            apprentiService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getApprentiById/{id}")
    public ResponseEntity<Apprenti> getApprentiById(@Parameter(description = "ID de l'apprenti") @PathVariable Long id) {
        return apprentiService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
