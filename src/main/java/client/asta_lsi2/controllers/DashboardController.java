package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.CustomUserDetailsService;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;

@Controller
public class DashboardController {

    private final ApprentiService apprentiService;
    private final CustomUserDetailsService customUserDetailsService;
    private final WebClient webClient;

    public DashboardController(ApprentiService apprentiService, CustomUserDetailsService customUserDetailsService, WebClient webClient)
    {
        this.apprentiService = apprentiService;
        this.customUserDetailsService = customUserDetailsService;
        this.webClient = webClient;
    }

    @GetMapping({ "/dashboard"})
    public String dashboard(Model model) {
        Year currentYear = Year.now();
        model.addAttribute("currentYear", currentYear);
        model.addAttribute("apprentis", apprentiService.listApprentisForYear(currentYear));

        System.out.println(apprentiService.listApprentisForYear(currentYear));
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
        //customUserDetailsService.saveApprenti(sample);// pour chiffrage du mdp
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
