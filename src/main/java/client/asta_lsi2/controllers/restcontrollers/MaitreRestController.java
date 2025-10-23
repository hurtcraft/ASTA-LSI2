package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.service.MaitreApprentissageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maitre")
@Tag(name = "Maitre apprentissage", description = "Gestion des maitres apprentissages")
public class MaitreRestController {
    private final MaitreApprentissageService maitreApprentissageService;
    public MaitreRestController(MaitreApprentissageService service) {
        this.maitreApprentissageService = service;
    }
    @Operation(summary = "Récupère un maitre d'apprentissage par email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maitre trouvé"),
            @ApiResponse(responseCode = "404", description = "Maitre non trouvé")
    })
    @GetMapping("/getMaitreByEmail/{email}")
    public ResponseEntity<MaitreApprentissage> getMaitreByEmail(@Parameter(description = "Email du maitre")@PathVariable String email) {
        return maitreApprentissageService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
