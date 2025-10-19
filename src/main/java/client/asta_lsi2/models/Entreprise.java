package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entrepriseId;

    @Column(nullable = false, length = 100)
    private String raisonSociale;

    @Column(nullable = false, length = 100)
    private String entrepriseAdresse;

    @Column(length = 512)
    private String infoAccesLocaux;

    public Entreprise() {
    }

    public Entreprise(Long entrepriseId, String raisonSociale, String entrepriseAdresse, String infoAccesLocaux) {
        this.entrepriseId = entrepriseId;
        this.raisonSociale = raisonSociale;
        this.entrepriseAdresse = entrepriseAdresse;
        this.infoAccesLocaux = infoAccesLocaux;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "entrepriseId=" + entrepriseId +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", entrepriseAdresse='" + entrepriseAdresse + '\'' +
                ", infoAccesLocaux='" + infoAccesLocaux + '\'' +
                '}';
    }
}