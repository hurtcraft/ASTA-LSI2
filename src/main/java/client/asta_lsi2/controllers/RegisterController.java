package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.Majeur;
import client.asta_lsi2.models.Programme;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.MaitreApprentissageService;
import client.asta_lsi2.service.MajeurService;
import client.asta_lsi2.service.AnneeAcademiqueCouranteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Year;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final ApprentiService apprentiService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final MajeurService majeurService;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;

    public RegisterController(ApprentiService apprentiService,MaitreApprentissageService maitreApprentissageService, MajeurService majeurService, AnneeAcademiqueCouranteService anneeAcademiqueCouranteService) {
        this.apprentiService = apprentiService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.majeurService = majeurService;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
    }

    @GetMapping("/apprenti")
    public String registerApprenti(Model model) {
        model.addAttribute("registerApprentiForm", new Apprenti());
        model.addAttribute("majeurs", majeurService.findAll());
        model.addAttribute("programmes", Programme.values());
        return "apprenti/register";
    }

    @GetMapping("/maitre")
    public String registerMaitre(Model model) {
        model.addAttribute("registerMaitreForm",new MaitreApprentissage());
        return "maitre/register";
    }

    @PostMapping("/apprenti")
    public String registerApprentiSubmit(@ModelAttribute Apprenti apprenti, 
                                       @RequestParam(required = false) Long majeurId,
                                       @RequestParam(required = false) String programme,
                                       Model model) {
        try {
            // Utiliser l'année académique courante configurée au lieu de Year.now()
            Year anneeDebut = anneeAcademiqueCouranteService.getAnneeDebut();
            apprenti.setAnneeAcademiqueDebut(anneeDebut);
            apprenti.setAnneeAcademiqueFin(anneeDebut.plusYears(1));
            
            // Convertir les IDs en objets AVANT la sauvegarde
            if (majeurId != null) {
                Majeur majeur = majeurService.findById(majeurId);
                apprenti.setMajeur(majeur);
            }
            
            if (programme != null && !programme.isEmpty()) {
                try {
                    apprenti.setProgramme(Programme.valueOf(programme));
                } catch (IllegalArgumentException e) {
                    // Programme invalide, on peut ajouter une gestion d'erreur ici
                }
            }
            
            apprentiService.save(apprenti);
            return "redirect:/dashboard";
            
        } catch (DataIntegrityViolationException e) {
            // Email déjà existant - reconstituer l'objet avec les bonnes valeurs
            if (majeurId != null) {
                Majeur majeur = majeurService.findById(majeurId);
                apprenti.setMajeur(majeur);
            }
            
            if (programme != null && !programme.isEmpty()) {
                try {
                    apprenti.setProgramme(Programme.valueOf(programme));
                } catch (IllegalArgumentException ex) {
                    // Programme invalide
                }
            }
            
            model.addAttribute("error", "Cet email est déjà utilisé. Veuillez en choisir un autre.");
            model.addAttribute("registerApprentiForm", apprenti);
            model.addAttribute("majeurs", majeurService.findAll());
            model.addAttribute("programmes", Programme.values());
            return "apprenti/register";
        }
    }

    @PostMapping("/maitre")
    public String registerMaitreSubmit(@ModelAttribute MaitreApprentissage maitreApprentissage, Model model) {

        maitreApprentissageService.save(maitreApprentissage);
        return "redirect:/login";
    }
}
