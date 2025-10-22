package client.asta_lsi2.controllers;


import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/maitre")

public class MaitreApprentissageController {
    private final WebClient webClient;
    public MaitreApprentissageController(WebClient webClient) {
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
        MaitreApprentissage maitre = webClient.get()
                .uri("/maitre/getMaitreByEmail/{email}", authentication.getName())
                .cookies(c -> c.add("JSESSIONID", finalJsessionId))
                .retrieve()
                .bodyToMono(MaitreApprentissage.class)
                .block();

        model.addAttribute("maitre", maitre );
        return "maitre/home";
    }
}
