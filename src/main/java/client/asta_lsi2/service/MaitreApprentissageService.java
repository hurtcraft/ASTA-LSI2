package client.asta_lsi2.service;


import client.asta_lsi2.models.MaitreApprentissage;
import client.asta_lsi2.repository.MaitreApprentissageRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaitreApprentissageService {

    private final MaitreApprentissageRepository maitreApprentissageRepository;
    private final PasswordEncoder passwordEncoder;
    public MaitreApprentissageService(MaitreApprentissageRepository maitreApprentissageRepository,PasswordEncoder passwordEncoder) {
        this.maitreApprentissageRepository = maitreApprentissageRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void save(MaitreApprentissage maitreApprentissage) {
        if (maitreApprentissage.getPassword() != null && !maitreApprentissage.getPassword().isBlank()) {
            maitreApprentissage.setPassword(passwordEncoder.encode(maitreApprentissage.getPassword()));
        }
        maitreApprentissageRepository.save(maitreApprentissage);
    }
    public Optional<MaitreApprentissage> findByEmail(String email) {
        return maitreApprentissageRepository.findByMaEmail(email);
    }
    public boolean maitreExistsByEmail(String email) {
        return maitreApprentissageRepository.existsByMaEmail(email);
    }

    public java.util.List<MaitreApprentissage> findAll() {
        return maitreApprentissageRepository.findAll();
    }

    public Optional<MaitreApprentissage> findById(Long id) {
        return maitreApprentissageRepository.findById(id);
    }
}
