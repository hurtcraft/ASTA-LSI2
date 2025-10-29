package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Majeur;
import client.asta_lsi2.service.MajeurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/majeurs")
public class MajeurController {
    
    private final MajeurService majeurService;
    
    public MajeurController(MajeurService majeurService) {
        this.majeurService = majeurService;
    }
    
    @GetMapping
    public String listMajeurs(Model model) {
        model.addAttribute("majeurs", majeurService.findAll());
        return "majeurs/list";
    }
    
    @GetMapping("/add")
    public String addMajeurForm(Model model) {
        model.addAttribute("majeur", new Majeur());
        return "majeurs/add";
    }
    
    @PostMapping("/add")
    public String addMajeur(@ModelAttribute Majeur majeur, Model model) {
        // Trim label and basic validation
        if (majeur.getLabel() != null) {
            majeur.setLabel(majeur.getLabel().trim());
        }

        if (majeur.getLabel() == null || majeur.getLabel().isEmpty()) {
            model.addAttribute("errorMessage", "Le libellé du majeur est requis.");
            model.addAttribute("majeur", majeur);
            return "majeurs/add";
        }

        // Check duplicate by label and show friendly error instead of throwing 500
        if (majeurService.existsByLabel(majeur.getLabel())) {
            model.addAttribute("errorMessage", "Cette majeure existe déjà.");
            model.addAttribute("majeur", majeur);
            return "majeurs/add";
        }

        try {
            majeurService.save(majeur);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            // Fallback: unique constraint violation
            model.addAttribute("errorMessage", "Cette majeure existe déjà.");
            model.addAttribute("majeur", majeur);
            return "majeurs/add";
        }

        return "redirect:/majeurs";
    }
    
    @GetMapping("/{id}/edit")
    public String editMajeurForm(@PathVariable Long id, Model model) {
        Majeur majeur = majeurService.findById(id);
        if (majeur != null) {
            model.addAttribute("majeur", majeur);
            return "majeurs/edit";
        }
        return "redirect:/majeurs";
    }
    
    @PostMapping("/{id}/edit")
    public String editMajeur(@PathVariable Long id, @ModelAttribute Majeur majeur) {
        Majeur existingMajeur = majeurService.findById(id);
        if (existingMajeur != null) {
            existingMajeur.setLabel(majeur.getLabel());
            majeurService.save(existingMajeur);
        }
        return "redirect:/majeurs";
    }
    
    @GetMapping("/{id}/delete")
    public String deleteMajeur(@PathVariable Long id) {
        majeurService.deleteById(id);
        return "redirect:/majeurs";
    }
}
