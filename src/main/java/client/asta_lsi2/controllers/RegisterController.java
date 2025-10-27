package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.Majeur;
import client.asta_lsi2.models.Programme;
import client.asta_lsi2.models.Role;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.CookiService;
import client.asta_lsi2.service.MajeurService;
import client.asta_lsi2.service.AnneeAcademiqueCouranteService;
import client.asta_lsi2.service.ApprentiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Year;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final WebClient webClient;
    private final MajeurService majeurService;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;
    private final ApprentiService apprentiService;
    private final CookiService cookiService;
    public RegisterController(WebClient webClient, MajeurService majeurService, AnneeAcademiqueCouranteService anneeAcademiqueCouranteService, ApprentiService apprentiService,CookiService cookiService) {
        this.webClient = webClient;
        this.majeurService = majeurService;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
        this.apprentiService = apprentiService;
        this.cookiService = cookiService;
    }

    @GetMapping("/apprenti")
    public String registerApprenti(Model model,HttpServletRequest request) {
        String jsessionId=cookiService.getJSessionId(request);
        model.addAttribute("registerApprentiForm", new Apprenti());
        model.addAttribute("majeurs", majeurService.findAll());
        model.addAttribute("programmes", Programme.values());
        // Charger entreprises et maîtres pour le formulaire
        var entreprises = webClient.get()
                .uri("/entreprises/all")
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToFlux(client.asta_lsi2.models.Entreprise.class)
                .collectList()
                .blockOptional()
                .orElse(java.util.Collections.emptyList());
        var maitres = webClient.get()
                .uri("/maitre/all")
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToFlux(client.asta_lsi2.models.MaitreApprentissage.class)
                .collectList()
                .blockOptional()
                .orElse(java.util.Collections.emptyList());
        model.addAttribute("entreprises", entreprises);
        model.addAttribute("maitres", maitres);
        return "apprenti/register";
    }

    @GetMapping("/apprenti/fragment")
    public String registerApprentiFragment(Model model,HttpServletRequest request) {
        // Réutilise la méthode précédente pour remplir le modèle
        registerApprenti(model,request);
        return "apprenti/register :: registerForm";
    }

    @GetMapping("/maitre")
    public String registerMaitre(Model model) {
        model.addAttribute("registerMaitreForm", new MaitreApprentissage());
        return "maitre/register";
    }

    @GetMapping("/tuteurEnseignant")
    public String registerTuteur(Model model) {
        model.addAttribute("registerTuteurForm", new TuteurEnseignant());
        return "tuteur/register";
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
            } else {
                // Aucun majeur fourni, retourner une erreur
                model.addAttribute("error", "Veuillez sélectionner un majeur.");
                model.addAttribute("registerApprentiForm", apprenti);
                model.addAttribute("majeurs", majeurService.findAll());
                model.addAttribute("programmes", Programme.values());
                return "apprenti/register";
            }
            
            if (programme != null && !programme.isEmpty()) {
                try {
                    apprenti.setProgramme(Programme.valueOf(programme));
                } catch (IllegalArgumentException e) {
                    // Programme invalide, utiliser I1 par défaut
                    apprenti.setProgramme(Programme.I1);
                }
            } else {
                // Aucun programme fourni, utiliser I1 par défaut
                apprenti.setProgramme(Programme.I1);
            }
            
            // Définir une entreprise par défaut si non fournie
            if (apprenti.getEntreprise() == null) {
                // Créer une entreprise par défaut ou utiliser une existante
                // Pour l'instant, on laisse null car ce n'est pas requis
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
    public String registerMaitreSubmit(@ModelAttribute MaitreApprentissage maitreApprentissage) {
        webClient.post()
                .uri("/register/maitre")
                .bodyValue(maitreApprentissage)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "redirect:/dashboard";
    }

    @PostMapping("/tuteurEnseignant")
    public String registerTuteurSubmit(@ModelAttribute TuteurEnseignant tuteurEnseignant, HttpServletRequest request) {
        webClient.post()
                .uri("/register/tuteurEnseignant")
                .bodyValue(tuteurEnseignant)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                tuteurEnseignant.getEmail(),
                tuteurEnseignant.getPassword(),
                List.of(new SimpleGrantedAuthority(
                        "ROLE_"+Role.TUTEUR_ENSEIGNANT.name()))
        );
        HttpSession session = request.getSession(true);
        session.setAttribute(
                "SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/dashboard?email=" + tuteurEnseignant.getEmail();
    }
}