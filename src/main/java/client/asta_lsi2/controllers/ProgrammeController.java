package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Programme;
import client.asta_lsi2.service.ProgrammeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/programmes")
public class ProgrammeController {
    
    private final ProgrammeService programmeService;
    
    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }
    
    @GetMapping
    public String listProgrammes(Model model) {
        model.addAttribute("programmes", programmeService.findAll());
        return "programmes/list";
    }
    
    @GetMapping("/add")
    public String addProgrammeForm(Model model) {
        model.addAttribute("programme", new Programme());
        return "programmes/add";
    }
    
    @PostMapping("/add")
    public String addProgramme(@ModelAttribute Programme programme) {
        programmeService.save(programme);
        return "redirect:/programmes";
    }
    
    @GetMapping("/{id}/edit")
    public String editProgrammeForm(@PathVariable Long id, Model model) {
        Programme programme = programmeService.findById(id);
        if (programme != null) {
            model.addAttribute("programme", programme);
            return "programmes/edit";
        }
        return "redirect:/programmes";
    }
    
    @PostMapping("/{id}/edit")
    public String editProgramme(@PathVariable Long id, @ModelAttribute Programme programme) {
        Programme existingProgramme = programmeService.findById(id);
        if (existingProgramme != null) {
            existingProgramme.setLabel(programme.getLabel());
            programmeService.save(existingProgramme);
        }
        return "redirect:/programmes";
    }
    
    @GetMapping("/{id}/delete")
    public String deleteProgramme(@PathVariable Long id) {
        programmeService.deleteById(id);
        return "redirect:/programmes";
    }
}
