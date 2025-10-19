package client.asta_lsi2.controllers;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.requests.LoginRequest;
import client.asta_lsi2.security.JwtUtils;
import client.asta_lsi2.service.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils, CustomUserDetailsService userDetailsService,PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtUtils.generateToken(userDetails.getUsername());


        System.out.println("token: " + jwtUtils.getEmailFromToken(token));
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registerApprenti")
    public ResponseEntity<String> registerApprenti(@RequestBody Apprenti apprenti) {

        if (userDetailsService.apprentiExists(apprenti.getApprentiEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Apprenti déjà existant");
        }

        userDetailsService.saveApprenti(apprenti);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Apprenti enregistré avec succès");
    }

    @PostMapping("/registerMaitre")
    public ResponseEntity<String> registerMaitre(@RequestBody MaitreApprentissage maitre) {

        if (userDetailsService.maitreExists(maitre.getMaEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Maitre d'apprentissage déjà existant");
        }

        userDetailsService.saveMaitre(maitre);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Maitre d'apprentissage enregistré avec succès");
    }


}