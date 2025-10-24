package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.MaitreApprentissageService;
import client.asta_lsi2.service.ProgrammeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final ApprentiService apprentiService;
    private final MaitreApprentissageService maitreApprentissageService;
    private final ProgrammeService programmeService;

    public RegisterController(ApprentiService apprentiService,MaitreApprentissageService maitreApprentissageService, ProgrammeService programmeService) {
        this.apprentiService = apprentiService;
        this.maitreApprentissageService = maitreApprentissageService;
        this.programmeService = programmeService;
    }

    @GetMapping("/apprenti")
    public String registerApprenti(Model model) {
        model.addAttribute("registerApprentiForm", new Apprenti());
        // TODO: Ajouter les listes de majeurs et programmes
        // model.addAttribute("majeurs", majeurService.findAll());
        model.addAttribute("programmes", programmeService.findAll());
        return "apprenti/register";
    }

    @GetMapping("/maitre")
    public String registerMaitre(Model model) {
        model.addAttribute("registerMaitreForm",new MaitreApprentissage());
        return "maitre/register";
    }

    @PostMapping("/apprenti")
    public String registerApprentiSubmit(@ModelAttribute Apprenti apprenti, Model model) {
        apprentiService.save(apprenti);
        return "redirect:/dashboard";
    }

    @PostMapping("/maitre")
    public String registerMaitreSubmit(@ModelAttribute MaitreApprentissage maitreApprentissage, Model model) {

        maitreApprentissageService.save(maitreApprentissage);
        return "redirect:/login";
    }
}
