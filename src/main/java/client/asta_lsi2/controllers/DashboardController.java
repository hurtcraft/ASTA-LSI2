package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.CookiService;
import client.asta_lsi2.service.TuteurEnseignantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;

@Controller
public class DashboardController {

    private final ApprentiService apprentiService;
    private final WebClient webClient;
    private final CookiService cookiService;
    private final TuteurEnseignantService tuteurEnseignantService;

    public DashboardController(ApprentiService apprentiService, WebClient webClient, CookiService cookiService, TuteurEnseignantService tuteurEnseignantService) {
        this.apprentiService = apprentiService;
        this.webClient = webClient;
        this.cookiService = cookiService;
        this.tuteurEnseignantService = tuteurEnseignantService;
    }

    @GetMapping({"/dashboard"})
    public String dashboard(@RequestParam(value = "email", required = false) String emailParam,
                            Model model) {
        Year currentYear = Year.now();

        String tuteurEmail;
        if (emailParam != null && !emailParam.isEmpty()) {
            tuteurEmail = emailParam;
        } else {
            tuteurEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        TuteurEnseignant tuteurEnseignant = tuteurEnseignantService.getTuteurEnseignantByEmail(tuteurEmail)
                .orElse(null);
        System.out.println(tuteurEnseignant.getNom()+" "+tuteurEnseignant.getPrenom());
        model.addAttribute("tuteur", tuteurEnseignant);
        model.addAttribute("apprentis", apprentiService.listApprentisForYear(currentYear));

        return "dashboard";
    }

    @GetMapping("/dashboard/seed")
    public String seedSampleApprenti() {
        Year currentYear = Year.now();

        Apprenti sample = new Apprenti();
        sample.setApprentiName("NomSample");
        sample.setApprentiPrenom("PrenomSample");
        sample.setApprentiEmail("sample" + System.currentTimeMillis() + "@example.com");
        sample.setTelephone("0000000000");
        sample.setApprentiYear(currentYear);
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
