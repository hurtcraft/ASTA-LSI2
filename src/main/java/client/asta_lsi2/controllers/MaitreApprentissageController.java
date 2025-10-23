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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/maitre")

public class MaitreApprentissageController {
    private final WebClient webClient;
    public MaitreApprentissageController(WebClient webClient) {
        this.webClient = webClient;
    }



    @GetMapping("/home")
    public String apprentiHome(Model model, Authentication authentication, HttpServletRequest request) {
        String jsessionId = getJSessionId(request);

        MaitreApprentissage maitre = webClient.get()
                .uri("/maitre/getMaitreByEmail/{email}", authentication.getName())
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToMono(MaitreApprentissage.class)
                .block();

        model.addAttribute("maitre", maitre );
        return "maitre/home";
    }


    private String getJSessionId(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
