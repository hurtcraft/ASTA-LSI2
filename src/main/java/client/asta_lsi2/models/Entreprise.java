package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entreprise_id;
    @Column(nullable = false,length = 100)
    private String raison_social;
    @Column(nullable = false,length = 100)
    private String entreprise_adresse;
    @Column(length = 512)
    private String info_acces_locaux;

    public Entreprise(Long entreprise_id, String raison_social, String entreprise_adresse, String info_acces_locaux) {
        this.entreprise_id = entreprise_id;
        this.raison_social = raison_social;
        this.entreprise_adresse = entreprise_adresse;
        this.info_acces_locaux = info_acces_locaux;
    }

    public Entreprise() {

    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "entreprise_id=" + entreprise_id +
                ", raison_social='" + raison_social + '\'' +
                ", entreprise_adresse='" + entreprise_adresse + '\'' +
                ", info_acces_locaux='" + info_acces_locaux + '\'' +
                '}';
    }
}
