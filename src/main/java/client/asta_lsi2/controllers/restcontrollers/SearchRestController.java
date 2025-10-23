package client.asta_lsi2.controllers.restcontrollers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchRestController {

    private final SearchService searchService;
    public SearchRestController(SearchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping("/searchByName/{name}")
    public ResponseEntity<List<Apprenti>> searchByName(@PathVariable String name) {
        return ResponseEntity.ok(searchService.findByApprentiName(name));
    }
    @GetMapping("/searchByEntreprise/{entrepriseName}")
    public ResponseEntity<List<Apprenti>> searchByEntreprise(@PathVariable String entrepriseName) {
        return ResponseEntity.ok(searchService.findByEntrepriseName(entrepriseName));
    }
    @GetMapping("/searchByMotCle/{motcle}")
    public ResponseEntity<List<Apprenti>> searchByMotCle(@PathVariable String motcle) {
        return ResponseEntity.ok(searchService.findApprentiByMissionMotCleContaining(motcle));
    }
    @GetMapping("/searchByAnne/{anne}")
    public ResponseEntity<List<Apprenti>> searchByAnne(@PathVariable int anne) {
        return ResponseEntity.ok(searchService.findByAnne(anne));
    }

}
