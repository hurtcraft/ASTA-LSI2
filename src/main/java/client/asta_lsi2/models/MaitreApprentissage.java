package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MaitreApprentissage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MA_id;

    @Column(nullable = false, length = 100)
    private String MA_nom;
    @Column(nullable = false, length = 100)
    private String MA_prenom;
    @Column(nullable = false, length = 100)
    private String MA_email;
    private String MA_telephone;
    @Column(nullable = false, length = 1024)
    private String remarque;

    @ManyToOne
    @JoinColumn(name = "poste_id")
    private Poste poste;

    public MaitreApprentissage() {}
    public MaitreApprentissage(Long MA_id, String MA_nom, String MA_prenom, String MA_email, String MA_telephone, String remarque) {
        this.MA_id = MA_id;
        this.MA_nom = MA_nom;
        this.MA_prenom = MA_prenom;
        this.MA_email = MA_email;
        this.MA_telephone = MA_telephone;
        this.remarque = remarque;
    }


    @Override
    public String toString() {
        return "MaitreApprentissage{" +
                "MA_id=" + MA_id +
                ", MA_nom='" + MA_nom + '\'' +
                ", MA_prenom='" + MA_prenom + '\'' +
                ", MA_email='" + MA_email + '\'' +
                ", MA_telephone='" + MA_telephone + '\'' +
                ", remarque='" + remarque + '\'' +
                '}';
    }
}
