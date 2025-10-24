package client.asta_lsi2.service;

import client.asta_lsi2.models.Role;
import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.models.User;
import client.asta_lsi2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TuteurEnseingnantService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public TuteurEnseingnantService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<TuteurEnseignant> getTuteurEnseignantByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .filter(u -> u.getRoles().contains(Role.TUTEUR_ENSEIGNANT.name()))
                .map(u->(TuteurEnseignant)u);
    }
    public void save(TuteurEnseignant tuteurEnseignant) {
        tuteurEnseignant.setPassword(passwordEncoder.encode(tuteurEnseignant.getPassword()));
        userRepository.save(tuteurEnseignant);
    }
}
