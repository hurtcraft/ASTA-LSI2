package client.asta_lsi2.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Majeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majeur_id;
    @Column(nullable = false,unique = true, length = 100)
    private String label;

    public Majeur(Long majeur_id, String label) {
        this.majeur_id = majeur_id;
        this.label = label;
    }

    public Majeur() {

    }

    @Override
    public String toString() {
        return "Majeur{" +
                "majeur_id=" + majeur_id +
                ", label='" + label + '\'' +
                '}';
    }
}
