package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.MaitreApprentissageService;
import client.asta_lsi2.service.ProgrammeService;
import client.asta_lsi2.service.TuteurEnseingnantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final WebClient webClient;
    private final ProgrammeService programmeService;

    public RegisterController(WebClient webClient, ProgrammeService programmeService) {
        this.webClient = webClient;
        this.programmeService = programmeService;
    }

    @GetMapping("/apprenti")
    public String registerApprenti(Model model) {
        model.addAttribute("registerApprentiForm", new Apprenti());
        model.addAttribute("programmes", programmeService.findAll());
        return "apprenti/register";
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
    public String registerApprentiSubmit(@ModelAttribute Apprenti apprenti) {
        webClient.post()
                .uri("/register/apprenti")
                .bodyValue(apprenti)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // appel synchrone (simple pour formulaire)
        return "redirect:/login";
    }

    @PostMapping("/maitre")
    public String registerMaitreSubmit(@ModelAttribute MaitreApprentissage maitreApprentissage) {
        webClient.post()
                .uri("/register/maitre")
                .bodyValue(maitreApprentissage)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "redirect:/login";
    }

    @PostMapping("/tuteurEnseignant")
    public String registerTuteurSubmit(@ModelAttribute TuteurEnseignant tuteurEnseignant) {
        webClient.post()
                .uri("/register/tuteurEnseignant")
                .bodyValue(tuteurEnseignant)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "redirect:/login";
    }
}