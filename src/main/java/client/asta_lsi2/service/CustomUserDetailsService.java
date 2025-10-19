package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.models.Role;
import client.asta_lsi2.repository.ApprentiRepository;
import client.asta_lsi2.repository.MaitreApprentissageRepository;
import client.asta_lsi2.security.JwtUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ApprentiRepository apprentiRepo;
    private final MaitreApprentissageRepository maitreRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    public CustomUserDetailsService(ApprentiRepository apprentiRepo, MaitreApprentissageRepository maitreRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.apprentiRepo = apprentiRepo;
        this.maitreRepo = maitreRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;

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
                        .password(a.getPassword() )
                        .roles(Role.APPRENTI.name())
                        .build())
                .orElseGet(() -> maitreRepo.findByMaEmail(email)
                        .map(m -> org.springframework.security.core.userdetails.User
                                .withUsername(m.getMaEmail())
                                .password(m.getPassword())
                                .roles(Role.MAITRE_APPRENTISSAGE.name())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé")));
    }

    public void saveApprenti(Apprenti apprenti) {
        apprenti.setPassword(passwordEncoder.encode(apprenti.getPassword()));
        apprentiRepo.save(apprenti);
    }
    public void saveMaitre(MaitreApprentissage maitre) {
        maitre.setPassword(passwordEncoder.encode(maitre.getPassword()));
        maitreRepo.save(maitre);
    }

    public boolean apprentiExists(String email){
        return apprentiRepo.findByApprentiName(email).isPresent();
    }
    public boolean maitreExists(String email){
        return maitreRepo.findByMaEmail(email).isPresent();
    }


}