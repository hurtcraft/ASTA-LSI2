package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.CookiService;
import client.asta_lsi2.service.TuteurEnseignantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import client.asta_lsi2.service.AnneeAcademiqueCouranteService;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;

@Controller
public class DashboardController {

    private final ApprentiService apprentiService;
    private final WebClient webClient;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;
    private final CookiService cookiService;
    private final TuteurEnseignantService tuteurEnseignantService;

    public DashboardController(ApprentiService apprentiService, WebClient webClient, AnneeAcademiqueCouranteService anneeAcademiqueCouranteService, CookiService cookiService, TuteurEnseignantService tuteurEnseignantService) {
        this.apprentiService = apprentiService;
        this.webClient = webClient;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
        this.cookiService = cookiService;
        this.tuteurEnseignantService = tuteurEnseignantService;
    }

    @GetMapping({ "/dashboard"})
    public String dashboard(@RequestParam(required = false) String tri, Model model) {
        Year currentYear = anneeAcademiqueCouranteService.getAnneeDebut();
        model.addAttribute("currentYear", currentYear);
        
        List<Apprenti> tousApprentis = apprentiService.findAll();
        
        if ("date" != null && "date".equals(tri)) {
            tousApprentis.sort((a1, a2) -> a2.getAnneeAcademiqueDebut().compareTo(a1.getAnneeAcademiqueDebut()));
        } else if ("programme" != null && "programme".equals(tri)) {
            tousApprentis.sort((a1, a2) -> a1.getProgramme().compareTo(a2.getProgramme()));
        } else if ("nom" != null && "nom".equals(tri)) {
            tousApprentis.sort((a1, a2) -> a1.getApprentiName().compareTo(a2.getApprentiName()));
        }
        
        model.addAttribute("apprentis", tousApprentis);
        model.addAttribute("triActuel", tri != null ? tri : "aucun");
        
        System.out.println("Total apprentis affichés: " + tousApprentis.size());
        return "dashboard";
    }

    @GetMapping("/dashboard/seed")
    public String seedSampleApprenti() {
        Apprenti sample = new Apprenti();
        sample.setApprentiName("NomSample");
        sample.setApprentiPrenom("PrenomSample");
        sample.setApprentiEmail("sample" + System.currentTimeMillis() + "@example.com");
        sample.setTelephone("0000000000");
        sample
                .setAnneeAcademiqueCourante();
        sample.setPassword("password");
        apprentiService.save(sample);
        return "redirect:/dashboard";
    }

    // Retourne le fragment Thymeleaf contenant les informations d'un apprenti (injection dans modal)
    @GetMapping("/apprenti/{id}/userinfo-fragment")
    public String apprentiUserinfoFragment(@PathVariable Long id, Model model) {
        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiById/{id}", id)
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        if (apprenti != null) {
            model.addAttribute("apprenti", apprenti);
        }
        return "apprenti/userinfo :: userinfoFragment";
    }

}
