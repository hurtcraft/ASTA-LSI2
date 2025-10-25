package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/search")
@Tag(name = "Search API", description = "API pour rechercher des apprentis selon différents critères")
public class SearchRestController {

    private final SearchService searchService;

    public SearchRestController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "Rechercher des apprentis par nom", description = "Renvoie la liste des apprentis dont le nom correspond au paramètre fourni")
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<List<Apprenti>> searchByName(
            @Parameter(description = "Nom de l'apprenti à rechercher", example = "Martin")
            @PathVariable String name) {
        return ResponseEntity.ok(searchService.findByApprentiName(name));
    }

    @Operation(summary = "Rechercher des apprentis par entreprise", description = "Renvoie la liste des apprentis travaillant dans l'entreprise spécifiée")
    @GetMapping("/searchByEntreprise/{entrepriseName}")
    public ResponseEntity<List<Apprenti>> searchByEntreprise(
            @Parameter(description = "Nom de l'entreprise", example = "Google")
            @PathVariable String entrepriseName) {
        return ResponseEntity.ok(searchService.findByEntrepriseName(entrepriseName));
    }

    @Operation(summary = "Rechercher des apprentis par mot-clé de mission", description = "Renvoie les apprentis dont les missions contiennent le mot-clé fourni")
    @GetMapping("/searchByMotCle/{motcle}")
    public ResponseEntity<List<Apprenti>> searchByMotCle(
            @Parameter(description = "Mot-clé à rechercher dans les missions", example = "Java")
            @PathVariable String motcle) {
        return ResponseEntity.ok(searchService.findApprentiByMissionMotCleContaining(motcle));
    }

    @Operation(summary = "Rechercher des apprentis par année", description = "Renvoie les apprentis inscrits pour l'année spécifiée")
    @GetMapping("/searchByAnne/{anne}")
    public ResponseEntity<List<Apprenti>> searchByAnne(
            @Parameter(description = "Année des apprentis", example = "2025")
            @PathVariable int anne) {
        return ResponseEntity.ok(searchService.findByAnne(anne));
    }

        @Operation(summary = "Récupérer tous les apprentis", description = "Renvoie la liste complète de tous les apprentis")
        @GetMapping("/all")
        public ResponseEntity<List<Apprenti>> getAllApprentis() {
            return ResponseEntity.ok(searchService.findAllApprentis());
        }
}