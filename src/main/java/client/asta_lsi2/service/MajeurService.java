package client.asta_lsi2.service;

import client.asta_lsi2.models.Majeur;
import client.asta_lsi2.repository.MajeurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajeurService {
    
    private final MajeurRepository majeurRepository;
    
    public MajeurService(MajeurRepository majeurRepository) {
        this.majeurRepository = majeurRepository;
    }
    
    public List<Majeur> findAll() {
        return majeurRepository.findAll();
    }
    
    public Majeur save(Majeur majeur) {
        return majeurRepository.save(majeur);
    }

    public boolean existsByLabel(String label) {
        return majeurRepository.findByLabel(label).isPresent();
    }
    
    public Majeur findById(Long id) {
        return majeurRepository.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
        majeurRepository.deleteById(id);
    }
}
