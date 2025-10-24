package client.asta_lsi2.repository;

import client.asta_lsi2.models.Majeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajeurRepository extends JpaRepository<Majeur, Long> {
}
