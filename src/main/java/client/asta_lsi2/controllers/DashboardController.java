package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.service.ApprentiService;
import client.asta_lsi2.service.AnneeAcademiqueCouranteService;
import client.asta_lsi2.service.TuteurEnseignantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;
import java.util.Comparator;
import java.util.List;

@Controller
public class DashboardController {

    private final ApprentiService apprentiService;
    private final AnneeAcademiqueCouranteService anneeAcademiqueCouranteService;
    private final TuteurEnseignantService tuteurEnseignantService;

    public DashboardController(ApprentiService apprentiService, AnneeAcademiqueCouranteService anneeAcademiqueCouranteService, TuteurEnseignantService tuteurEnseignantService) {
        this.apprentiService = apprentiService;
        this.anneeAcademiqueCouranteService = anneeAcademiqueCouranteService;
        this.tuteurEnseignantService = tuteurEnseignantService;
    }

    @GetMapping({"/dashboard"})
    public String dashboard(@RequestParam(required = false) String tri,
                            @RequestParam(required = false) String inverse,
                            Model model) {
        Year currentYear = anneeAcademiqueCouranteService.getAnneeDebut();
        model.addAttribute("currentYear", currentYear);

        List<Apprenti> tousApprentis = apprentiService.findAll();

        // Déterminer si le tri doit être inversé
        boolean triInverse = "true".equals(inverse);

        if ("id".equals(tri)) {
            if (triInverse) {
                tousApprentis.sort((a1, a2) -> Long.compare(a2.getApprentiId(), a1.getApprentiId()));
            } else {
                tousApprentis.sort((a1, a2) -> Long.compare(a1.getApprentiId(), a2.getApprentiId()));
            }
        } else if ("programme".equals(tri)) {
            if (triInverse) {
                tousApprentis.sort((a1, a2) -> a2.getProgramme().compareTo(a1.getProgramme()));
            } else {
                tousApprentis.sort((a1, a2) -> a1.getProgramme().compareTo(a2.getProgramme()));
            }
        } else if ("nom".equals(tri)) {
            if (triInverse) {
                tousApprentis.sort((a1, a2) -> a2.getApprentiName().compareTo(a1.getApprentiName()));
            } else {
                tousApprentis.sort((a1, a2) -> a1.getApprentiName().compareTo(a2.getApprentiName()));
            }
        }
        String tuteurEmail=SecurityContextHolder.getContext().getAuthentication().getName();

        TuteurEnseignant tuteurEnseignant = getTuteurEnseignant(tuteurEmail);

        model.addAttribute("apprentis", tousApprentis);
        model.addAttribute("triActuel", tri != null ? tri : "aucun");
        model.addAttribute("triInverse", triInverse);
        model.addAttribute("tuteur",tuteurEnseignant);
        return "dashboard";
    }

    @GetMapping("/dashboard/seed")
    public String seedSampleApprenti() {
        Apprenti sample = new Apprenti();
        sample.setApprentiName("NomSample");
        sample.setApprentiPrenom("PrenomSample");
        sample.setApprentiEmail("sample" + System.currentTimeMillis() + "@example.com");
        sample.setTelephone("0000000000");
        sample.setAnneeAcademiqueCourante();
        apprentiService.save(sample);
        return "redirect:/dashboard";
    }

    private TuteurEnseignant getTuteurEnseignant(String emailParam) {
        String tuteurEmail;
        if (emailParam != null && !emailParam.isEmpty()) {
            tuteurEmail = emailParam;
        } else {
            tuteurEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        return tuteurEnseignantService.getTuteurEnseignantByEmail(tuteurEmail)
                .orElse(null);

    }

}
