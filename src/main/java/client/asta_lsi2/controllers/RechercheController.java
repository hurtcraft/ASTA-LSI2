package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.CookiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Controller
public class RechercheController {

    private final WebClient webClient; // baseUrl: http://localhost:8080/api
    private final CookiService cookiService;

    public RechercheController(WebClient webClient, CookiService cookiService) {
        this.webClient = webClient;
        this.cookiService = cookiService;
    }

    // Fragment: lignes du tableau filtrées (utilisé par le dashboard)
    @GetMapping("/dashboard/search")
    public String search(@RequestParam(name = "q", required = false) String q, Model model, HttpServletRequest request) {
        List<Apprenti> results;
        String jsessionId = cookiService.getJSessionId(request);

        if (q == null || q.isBlank()) {
            // Retourner tous les apprentis quand query vide
            try {
                results = webClient.get()
                        .uri("/search/all")
                        .cookies(c -> c.add("JSESSIONID", jsessionId))
                        .retrieve()
                        .bodyToFlux(Apprenti.class)
                        .collectList()
                        .onErrorReturn(Collections.emptyList())
                        .block();
            } catch (Exception e) {
                results = Collections.emptyList();

            }
        } else {
            String query = q.trim();

            // Si c'est une année numérique, chercher uniquement par année
            try {
                int annee = Integer.parseInt(query);
                results = callSearchEndpoint("/search/searchByAnne/{val}", annee,request);
                model.addAttribute("apprentis", results);
                return "fragments/apprenti-rows :: rows";
            } catch (NumberFormatException ignored) {
            }

            // Sinon combiner les recherches
            Set<Apprenti> set = new LinkedHashSet<>();

            // Appels aux endpoints REST de SearchRestController (base /api/search)
            try {
                set.addAll(callSearchEndpoint("/search/searchByName/{val}", query,request));
            } catch (Exception ignored) {
            }
            try {
                set.addAll(callSearchEndpoint("/search/searchByEntreprise/{val}", query,request));
            } catch (Exception ignored) {
            }
            try {
                set.addAll(callSearchEndpoint("/search/searchByMotCle/{val}", query,request));
            } catch (Exception ignored) {
            }

            results = new ArrayList<>(set);
        }

        model.addAttribute("apprentis", results);
        return "fragments/apprenti-rows :: rows";
    }

    // Fragment: suggestions rapides (liste compact)
    @GetMapping("/dashboard/suggest")
    public String suggest(@RequestParam(name = "q", required = false) String q, Model model,HttpServletRequest request) {
        List<Apprenti> suggestions;
        if (q == null || q.isBlank()) {
            suggestions = Collections.emptyList();
        } else {
            String query = q.trim();
            Set<Apprenti> set = new LinkedHashSet<>();
            try {
                set.addAll(callSearchEndpoint("/search/searchByName/{val}", query,request));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            try {
                set.addAll(callSearchEndpoint("/search/searchByEntreprise/{val}", query,request));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            try {
                set.addAll(callSearchEndpoint("/search/searchByMotCle/{val}", query,request));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            try {
                int annee = Integer.parseInt(query);
                set.addAll(callSearchEndpoint("/search/searchByAnne/{val}", annee,request));
            } catch (NumberFormatException ignored) {
            }

            suggestions = new ArrayList<>(set);
            if (suggestions.size() > 10) suggestions = suggestions.subList(0, 10);
        }

        model.addAttribute("apprentis", suggestions);
        return "fragments/search-suggestions :: list";
    }

    private List<Apprenti> callSearchEndpoint(String pathTemplate, Object value, HttpServletRequest request) {
        String jsessionId = cookiService.getJSessionId(request);

        return webClient.get()
                .uri(pathTemplate, value)
                .cookies(c -> c.add("JSESSIONID", jsessionId))
                .retrieve()
                .bodyToFlux(Apprenti.class)
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());
    }
}
