package client.asta_lsi2.controllers;


import client.asta_lsi2.models.Role;
import client.asta_lsi2.models.requests.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller

public class LoginController {

    private final AuthenticationManager authenticationManager;
    private static final Map<String, String> ROLE_HOME_MAP = Map.of(
            "ROLE_" + Role.TUTEUR_ENSEIGNANT, "/dashboard"
    );

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginRequest());
        return "login";
    }


    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequest loginRequest, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

            Authentication auth = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            System.out.println(loginRequest);
            Optional<String> redirectUrl = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(ROLE_HOME_MAP::get)
                    .filter(Objects::nonNull)
                    .findFirst();

            userDetails.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));
            System.out.println("USER " + userDetails);
            return "redirect:"+ redirectUrl.orElse("/login");
        } catch (AuthenticationException ex) {
            System.out.println("Échec de connexion pour : " + loginRequest.getEmail());
            redirectAttributes.addFlashAttribute("errorMessage", "Email ou mot de passe incorrect.");

            return "redirect:/login?error";
        }
    }
}
