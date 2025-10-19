package client.asta_lsi2.controllers;

import client.asta_lsi2.service.ApprentiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Year;

@Controller
public class DashboardController {

	private final ApprentiService apprentiService;

	public DashboardController(ApprentiService apprentiService) {
		this.apprentiService = apprentiService;
	}

	@GetMapping({"/","/dashboard"})
	public String dashboard(Model model) {
		Year currentYear = Year.now();
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("apprentis", apprentiService.listApprentisForYear(currentYear));

		System.out.println(apprentiService.listApprentisForYear(currentYear));
		return "dashboard";
	}

	@GetMapping("/dashboard/seed")
	public String seedSampleApprenti() {
		Year currentYear = Year.now();
		// create a basic sample apprenti
		client.asta_lsi2.models.Apprenti sample = new client.asta_lsi2.models.Apprenti();
		sample.setApprentiName("NomSample");
		sample.setApprentiPrenom("PrenomSample");
		sample.setApprentiEmail("sample" + System.currentTimeMillis() + "@example.com");
		sample.setTelephone("0000000000");
		sample.setApprentiYear(currentYear);
		apprentiService.save(sample);
		return "redirect:/dashboard";
	}

}
