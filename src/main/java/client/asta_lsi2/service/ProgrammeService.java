package client.asta_lsi2.service;

import client.asta_lsi2.models.Programme;
import client.asta_lsi2.repository.ProgrammeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgrammeService {
    
    private final ProgrammeRepository programmeRepository;
    
    public ProgrammeService(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }
    
    public List<Programme> findAll() {
        return programmeRepository.findAll();
    }
    
    public Programme save(Programme programme) {
        return programmeRepository.save(programme);
    }
    
    public Programme findById(Long id) {
        return programmeRepository.findById(id).orElse(null);
    }
    
    public void deleteById(Long id) {
        programmeRepository.deleteById(id);
    }
}
