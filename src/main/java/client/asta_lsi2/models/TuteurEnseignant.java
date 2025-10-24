package client.asta_lsi2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TuteurEnseignant extends User {

    public TuteurEnseignant(String email, String password, String nom, String prenom) {
        super(email, password, List.of(Role.TUTEUR_ENSEIGNANT.name()), nom, prenom);

    }

    // set le role avant insertion
    @PrePersist
    public void prePersist() {
        if (getRoles() == null) {
            setRoles(List.of(Role.TUTEUR_ENSEIGNANT.name()));
        }
    }

    public TuteurEnseignant() {
        super();
    }
}
