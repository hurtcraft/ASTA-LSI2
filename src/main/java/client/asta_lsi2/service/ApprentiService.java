package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.repository.ApprentiRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {

	private final ApprentiRepository apprentiRepository;

	public ApprentiService(ApprentiRepository apprentiRepository) {
		this.apprentiRepository = apprentiRepository;
	}

	public List<Apprenti> listApprentisForYear(Year year) {
		return apprentiRepository.findByAnneeAcademiqueDebut(year);
	}

	public boolean apprentiExistsByEmail(String email) {
		return  apprentiRepository.existsByApprentiEmail(email);
	}

	public Optional<Apprenti> findApprentiByEmail(String email) {
		return apprentiRepository.findByApprentiEmail(email);
	}
	public void save(Apprenti apprenti) {
		apprentiRepository.save(apprenti);
	}
	public List<Apprenti> findByApprentiName(String apprentiName) {
		return apprentiRepository.findApprentisByApprentiName(apprentiName);
	}

	public Optional<Apprenti> findById(Long id) {
		return apprentiRepository.findById(id);
	}
	public List<Apprenti> findByApprentiEntrepriseRaisonSociale(String raisonSociale) {
		return apprentiRepository.findApprentisByEntrepriseRaisonSociale(raisonSociale);
	}
	public List<Apprenti> findByAnne(int anne){
		return apprentiRepository.findByAnneeAcademiqueDebut(Year.of(anne));
	}
	
	public List<Apprenti> findAll() {
		return apprentiRepository.findAll();
	}
	
	public void deleteById(Long id) {
		apprentiRepository.deleteById(id);
	}

}
