package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ApprentiController {

    private final ApprentiService apprentiService;

    public ApprentiController(ApprentiService apprentiService) {
        this.apprentiService = apprentiService;
    }

    @GetMapping("/apprenti/{id}")
    public String viewApprenti(@PathVariable Long id, Model model) {
        Optional<Apprenti> apprenti = apprentiService.findById(id);
        
        if (apprenti.isPresent()) {
            model.addAttribute("apprenti", apprenti.get());
            return "apprenti/userinfo";
        } else {
            return "redirect:/dashboard";
        }
    }
}
