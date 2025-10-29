package client.asta_lsi2.controllers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.Programme;
import client.asta_lsi2.service.CookiService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/apprenti")

public class ApprentiController {

    private final WebClient webClient; //pointe sur http://localhost:8080/api par défaut
    private final CookiService cookieService;
    public ApprentiController(WebClient webClient,CookiService cookieService)
    {
        this.webClient = webClient;
        this.cookieService = cookieService;
    }




    @GetMapping("/home")
    public String apprentiHome(Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = cookieService.getJSessionId(request);


        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiByEmail/{email}", authentication.getName())
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        return "apprenti/home";
    }

    @DeleteMapping("/deleteapprenti/{id}")
    public String deleteApprenti(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        String jsessionId = cookieService.getJSessionId(request);


        HttpStatusCode statusCode = webClient.delete()
                .uri("/deleteApprenti/{id}", id)
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode())
                .block();

        if (statusCode == HttpStatus.NO_CONTENT) {
            return "redirect:/logout";
        } else {
            return "redirect:/apprenti/home?error=true";
        }
    }

    @GetMapping("/edit/{id}")
    public String editApprenti(@PathVariable Long id, Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = cookieService.getJSessionId(request);


        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiById/{id}", id)
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        // Populate programmes for select preselection
        model.addAttribute("programmes", Programme.values());
    // Fetch entreprises, maitres, and majors via REST
    // Fetch entreprises and maitres via REST
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
    var majors = webClient.get()
        .uri("/majeurs/all")
        .cookies(c -> c.add("JSESSIONID", jsessionId))
        .retrieve()
        .bodyToFlux(client.asta_lsi2.models.Majeur.class)
        .collectList()
        .blockOptional()
        .orElse(java.util.Collections.emptyList());
    model.addAttribute("entreprises", entreprises);
    model.addAttribute("maitres", maitres);
    model.addAttribute("majors", majors);
        return "apprenti/useredit";
    }

    @PostMapping("/edit/{id}")
    public String updateApprenti(@PathVariable Long id, Apprenti apprentiForm, Authentication authentication, HttpServletRequest request, org.springframework.ui.Model model) {
        String jsessionId = cookieService.getJSessionId(request);
        try {
            webClient.patch()
                    .uri("/apprenti/editApprenti/{id}", id)
                    .cookies(c -> c.add("JSESSIONID", jsessionId))
                    .bodyValue(apprentiForm)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            return "redirect:/dashboard";
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                // Duplicate email: re-render the edit form with the submitted data and an error message
                model.addAttribute("apprenti", apprentiForm);
                model.addAttribute("programmes", Programme.values());
                // Fetch entreprises, maitres, and majors to populate selects (best-effort)
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
                var majors = webClient.get()
                        .uri("/majeurs/all")
                        .cookies(c -> c.add("JSESSIONID", jsessionId))
                        .retrieve()
                        .bodyToFlux(client.asta_lsi2.models.Majeur.class)
                        .collectList()
                        .blockOptional()
                        .orElse(java.util.Collections.emptyList());
                model.addAttribute("entreprises", entreprises);
                model.addAttribute("maitres", maitres);
                model.addAttribute("majors", majors);
                model.addAttribute("errorMessage", "Un apprenti a déjà ce mail");
                return "apprenti/useredit";
            }
            throw e;
        }
    }

    @GetMapping("/{id}/userinfo-fragment")
    public String getUserInfoFragment(@PathVariable Long id, Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = cookieService.getJSessionId(request);


        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiById/{id}", id)
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        return "apprenti/userinfo :: userinfoFragment";
    }
}
