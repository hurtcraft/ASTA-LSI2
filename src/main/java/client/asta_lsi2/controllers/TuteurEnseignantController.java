package client.asta_lsi2.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tuteurEnseignant")
public class TuteurEnseignantController {
    @GetMapping("/home")
    public String home() {
        return "tuteurEnseignant/home";
    }


}
