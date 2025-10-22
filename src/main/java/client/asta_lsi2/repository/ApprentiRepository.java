package client.asta_lsi2.repository;

import aj.org.objectweb.asm.commons.Remapper;
import client.asta_lsi2.models.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface ApprentiRepository extends JpaRepository<Apprenti, Long> {

	//@Query("SELECT a FROM Apprenti a WHERE a.apprentiYear = :year")
	//List<Apprenti> findByYear(@Param("year") Year year);
	List<Apprenti> findByApprentiYear(Year year);
	Optional<Apprenti> findByApprentiId(Long apprentiId);
	Optional<Apprenti>	findByApprentiName(String apprentiName);
	boolean existsByApprentiEmail(String email);
	Optional<Apprenti> findByApprentiEmail(String username);
}
