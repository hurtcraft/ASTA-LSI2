package client.asta_lsi2.controllers;

import client.asta_lsi2.models.ApprentiArchive;
import client.asta_lsi2.service.AnneeAcademiqueService;
import client.asta_lsi2.service.AnneeAcademiqueCouranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Year;
import java.util.List;

@Controller
@RequestMapping("/annee-academique")
public class AnneeAcademiqueController {
    
    private final AnneeAcademiqueService anneeAcademiqueService;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;
    
    public AnneeAcademiqueController(AnneeAcademiqueService anneeAcademiqueService, AnneeAcademiqueCouranteService anneeAcademiqueCouranteService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
    }
    
    /**
     * Affiche la page de gestion des années académiques
     */
    @GetMapping
    public String gestionAnneeAcademique(Model model) {
        AnneeAcademiqueService.AnneeAcademiqueStats stats = anneeAcademiqueService.getStatsAnneeCourante();
        model.addAttribute("stats", stats);
        model.addAttribute("anneeCourante", anneeAcademiqueCouranteService.getAnneeDebut());
        return "annee-academique/gestion";
    }
    
    /**
     * Crée une nouvelle année académique
     */
    @PostMapping("/nouvelle-annee")
    public String creerNouvelleAnnee(RedirectAttributes redirectAttributes) {
        try {
            anneeAcademiqueService.creerNouvelleAnneeAcademique();
            redirectAttributes.addFlashAttribute("success", 
                "Nouvelle année académique créée avec succès ! Les apprentis ont été promus et archivés.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Erreur lors de la création de la nouvelle année académique : " + e.getMessage());
        }
        return "redirect:/annee-academique";
    }
    
    /**
     * Affiche les archives d'une année donnée
     */
    @GetMapping("/archives")
    public String voirArchives(@RequestParam(required = false) Integer annee, Model model) {
        Year anneeRecherche = annee != null ? Year.of(annee) : anneeAcademiqueCouranteService.getAnneeDebut().minusYears(1);
        
        List<ApprentiArchive> archives = anneeAcademiqueService.getArchivesParAnnee(anneeRecherche);
        
        model.addAttribute("archives", archives);
        model.addAttribute("anneeRecherche", anneeRecherche);
        model.addAttribute("anneesDisponibles", getAnneesDisponibles());
        
        return "annee-academique/archives";
    }
    
    /**
     * Obtient les années disponibles pour la recherche d'archives
     */
    private List<Year> getAnneesDisponibles() {
        Year anneeCourante = anneeAcademiqueCouranteService.getAnneeDebut();
        return List.of(
            anneeCourante.minusYears(3),
            anneeCourante.minusYears(2),
            anneeCourante.minusYears(1),
            anneeCourante
        );
    }
}