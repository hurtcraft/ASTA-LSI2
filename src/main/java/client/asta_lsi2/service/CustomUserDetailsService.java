package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.Role;
import client.asta_lsi2.repository.ApprentiRepository;
import client.asta_lsi2.repository.MaitreApprentissageRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ApprentiRepository apprentiRepo;
    private final MaitreApprentissageRepository maitreRepo;
    public CustomUserDetailsService(ApprentiRepository apprentiRepo, MaitreApprentissageRepository maitreRepo ) {
        this.apprentiRepo = apprentiRepo;
        this.maitreRepo = maitreRepo;

    }
    /***
     *
     * @param email email de l'user à charger
     * @return
     * @throws UsernameNotFoundException si utilisateur non trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return apprentiRepo.findByApprentiEmail(email)
                .map(a -> org.springframework.security.core.userdetails.User
                        .withUsername(a.getApprentiEmail())
                        .password(a.getPassword() == null ? "" : a.getPassword())
                        .roles(Role.APPRENTI.name())
                        .build())
                .orElseGet(() -> maitreRepo.findByMaEmail(email)
                        .map(m -> org.springframework.security.core.userdetails.User
                                .withUsername(m.getMaEmail())
                                .password(m.getPassword() == null ? "" : m.getPassword())
                                .roles(Role.MAITRE_APPRENTISSAGE.name())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé")));
    }

}