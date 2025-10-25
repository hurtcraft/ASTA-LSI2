package client.asta_lsi2.controllers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.Programme;
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
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/apprenti")

public class ApprentiController {

    private final WebClient webClient; //pointe sur http://localhost:8080/api par défaut

    public ApprentiController(WebClient webClient) {
        this.webClient = webClient;
    }




    @GetMapping("/home")
    public String apprentiHome(Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionId = cookie.getValue();
                    break;
                }
            }
        }

        String finalJsessionId = jsessionId;
        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiByEmail/{email}", authentication.getName())
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        return "apprenti/home";
    }

    @DeleteMapping("/deleteapprenti/{id}")
    public String deleteApprenti(@PathVariable Long id, Authentication authentication, HttpServletRequest request) {
        String jsessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionId = cookie.getValue();
                    break;
                }
            }
        }

        String finalJsessionId = jsessionId;
        HttpStatusCode statusCode = webClient.delete()
                .uri("/deleteApprenti/{id}", id)
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
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
        String jsessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionId = cookie.getValue();
                    break;
                }
            }
        }

        String finalJsessionId = jsessionId;
        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiById/{id}", id)
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        // Populate programmes for select preselection
        model.addAttribute("programmes", Programme.values());
    // Fetch entreprises and maitres via REST
    var entreprises = webClient.get()
        .uri("/entreprises/all")
        .cookies(c -> c.add("JSESSIONID", finalJsessionId))
        .retrieve()
        .bodyToFlux(client.asta_lsi2.models.Entreprise.class)
        .collectList()
        .blockOptional()
        .orElse(java.util.Collections.emptyList());
    var maitres = webClient.get()
        .uri("/maitre/all")
        .cookies(c -> c.add("JSESSIONID", finalJsessionId))
        .retrieve()
        .bodyToFlux(client.asta_lsi2.models.MaitreApprentissage.class)
        .collectList()
        .blockOptional()
        .orElse(java.util.Collections.emptyList());
    model.addAttribute("entreprises", entreprises);
    model.addAttribute("maitres", maitres);
        return "apprenti/useredit";
    }

    @PostMapping("/edit/{id}")
    public String updateApprenti(@PathVariable Long id, Apprenti apprentiForm, Authentication authentication, HttpServletRequest request) {
        String jsessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionId = cookie.getValue();
                    break;
                }
            }
        }
        String finalJsessionId = jsessionId;

    webClient.patch()
        .uri("/apprenti/editApprenti/{id}", id)
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
                .bodyValue(apprentiForm)
                .retrieve()
                .toBodilessEntity()
                .block();

        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/userinfo-fragment")
    public String getUserInfoFragment(@PathVariable Long id, Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    jsessionId = cookie.getValue();
                    break;
                }
            }
        }

        String finalJsessionId = jsessionId;
        Apprenti apprenti = webClient.get()
                .uri("/apprenti/getApprentiById/{id}", id)
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
                .retrieve()
                .bodyToMono(Apprenti.class)
                .block();

        model.addAttribute("apprenti", apprenti);
        return "apprenti/userinfo :: userinfoFragment";
    }
}
