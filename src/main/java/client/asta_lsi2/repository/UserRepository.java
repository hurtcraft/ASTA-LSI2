package client.asta_lsi2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import client.asta_lsi2.models.TuteurEnseignant;
import client.asta_lsi2.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

}