package client.asta_lsi2.models;



import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;


@Data
@Entity

public class Apprenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprenti_id;

    @Column(nullable = false, length = 100)
    private String apprenti_name;
    @Column(nullable = false, length = 100)
    private String apprenti_prenom;
    @Column(nullable = false,unique = true, length = 100)
    private String apprenti_email;

    @Column(nullable = false, length = 100)
    private Year apprenti_year;

    @ManyToOne
    @JoinColumn(name = "majeur_id")
    private Majeur majeur;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;
    private String telephone;


    public Apprenti(long apprenti_id, String apprenti_name, String apprenti_prenom, String apprenti_email, String telephone) {
        this.apprenti_id = apprenti_id;
        this.apprenti_name = apprenti_name;
        this.apprenti_prenom = apprenti_prenom;
        this.apprenti_email = apprenti_email;
        this.telephone = telephone;
    }
    public Apprenti() {}

    @Override
    public String toString() {
        return "Apprenti{" +
                "apprenti_id=" + apprenti_id +
                ", apprenti_name='" + apprenti_name + '\'' +
                ", apprenti_prenom='" + apprenti_prenom + '\'' +
                ", apprenti_email='" + apprenti_email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
