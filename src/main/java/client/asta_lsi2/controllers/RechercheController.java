package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Controller
public class RechercheController {

	private final WebClient webClient; // baseUrl: http://localhost:8080/api

	public RechercheController(WebClient webClient) {
		this.webClient = webClient;
	}

	// Fragment: lignes du tableau filtrées (utilisé par le dashboard)
	@GetMapping("/dashboard/search")
	public String search(@RequestParam(name = "q", required = false) String q, Model model) {
		List<Apprenti> results;

		if (q == null || q.isBlank()) {
			try {
				results = webClient.get()
						.uri("/apprenti/all")
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
			Set<Apprenti> set = new LinkedHashSet<>();

			// Appels aux endpoints REST de SearchRestController (base /api/search)
			try { set.addAll(callSearchEndpoint("/search/searchByName/{val}", query)); } catch (Exception ignored) {}
			try { set.addAll(callSearchEndpoint("/search/searchByEntreprise/{val}", query)); } catch (Exception ignored) {}
			try { set.addAll(callSearchEndpoint("/search/searchByMotCle/{val}", query)); } catch (Exception ignored) {}

			try {
				int annee = Integer.parseInt(query);
				set.addAll(callSearchEndpoint("/search/searchByAnne/{val}", annee));
			} catch (NumberFormatException ignored) {}

			results = new ArrayList<>(set);
		}

		model.addAttribute("apprentis", results);
		return "fragments/apprenti-rows :: rows";
	}

	// Fragment: suggestions rapides (liste compact)
	@GetMapping("/dashboard/suggest")
	public String suggest(@RequestParam(name = "q", required = false) String q, Model model) {
		List<Apprenti> suggestions;
		if (q == null || q.isBlank()) {
			suggestions = Collections.emptyList();
		} else {
			String query = q.trim();
			Set<Apprenti> set = new LinkedHashSet<>();
			try { set.addAll(callSearchEndpoint("/search/searchByName/{val}", query)); } catch (Exception ignored) {}
			try { set.addAll(callSearchEndpoint("/search/searchByEntreprise/{val}", query)); } catch (Exception ignored) {}
			try { set.addAll(callSearchEndpoint("/search/searchByMotCle/{val}", query)); } catch (Exception ignored) {}
			try {
				int annee = Integer.parseInt(query);
				set.addAll(callSearchEndpoint("/search/searchByAnne/{val}", annee));
			} catch (NumberFormatException ignored) {}

			suggestions = new ArrayList<>(set);
			if (suggestions.size() > 10) suggestions = suggestions.subList(0, 10);
		}

		model.addAttribute("apprentis", suggestions);
		return "fragments/search-suggestions :: list";
	}

	private List<Apprenti> callSearchEndpoint(String pathTemplate, Object value) {
		return webClient.get()
				.uri(pathTemplate, value)
				.retrieve()
				.bodyToFlux(Apprenti.class)
				.collectList()
				.blockOptional()
				.orElse(Collections.emptyList());
	}
}
