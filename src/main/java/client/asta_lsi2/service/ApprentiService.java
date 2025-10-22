package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.repository.ApprentiRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {

	private final ApprentiRepository apprentiRepository;
	private final PasswordEncoder passwordEncoder;


	public ApprentiService(ApprentiRepository apprentiRepository,PasswordEncoder passwordEncoder) {
		this.apprentiRepository = apprentiRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Apprenti> listApprentisForYear(Year year) {
		return apprentiRepository.findByApprentiYear(year);
	}

	public boolean apprentiExistsByEmail(String email) {
		return  apprentiRepository.existsByApprentiEmail(email);
	}

	public Optional<Apprenti> findApprentiByEmail(String email) {
		return apprentiRepository.findByApprentiEmail(email);
	}
	public void save(Apprenti apprenti) {
		apprenti.setPassword(passwordEncoder.encode(apprenti.getPassword()));
		apprentiRepository.save(apprenti);
	}

	public Optional<Apprenti> findById(Long id) {
		return apprentiRepository.findById(id);
	}

}
