package client.asta_lsi2.controllers.restcontrollers;

import client.asta_lsi2.models.Majeur;
import client.asta_lsi2.service.MajeurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/majeurs")
@Tag(name = "Majeur", description = "Gestion des majeurs")
public class MajeurRestController {

    private final MajeurService majeurService;

    public MajeurRestController(MajeurService majeurService) {
        this.majeurService = majeurService;
    }

    @Operation(summary = "Lister tous les majeurs")
    @GetMapping("/all")
    public ResponseEntity<List<Majeur>> getAll() {
        return ResponseEntity.ok(majeurService.findAll());
    }
}
