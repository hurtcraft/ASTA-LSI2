package client.asta_lsi2.service;

import client.asta_lsi2.models.Apprenti;
import client.asta_lsi2.repository.ApprentiRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class ApprentiService {

	private final ApprentiRepository apprentiRepository;

	public ApprentiService(ApprentiRepository apprentiRepository) {
		this.apprentiRepository = apprentiRepository;
	}

	public List<Apprenti> listApprentisForYear(Year year) {
		return apprentiRepository.findByApprentiYear(year);
	}

	public Apprenti save(Apprenti apprenti) {
		return apprentiRepository.save(apprenti);
	}

}
