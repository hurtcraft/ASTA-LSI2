package client.asta_lsi2.repository;

import client.asta_lsi2.models.Apprenti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;

public interface ApprentiRepository extends JpaRepository<Apprenti, Long> {

	@Query("SELECT a FROM Apprenti a WHERE a.apprenti_year = :year")
	List<Apprenti> findByYear(@Param("year") Year year);

}
