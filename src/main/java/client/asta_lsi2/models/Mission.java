package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mission_id;

    @Column(nullable = false, length = 512)
    private String mot_cle;

    @Column(nullable = false, length = 100)
    private String metier_cible;
    @Column(nullable = false, length = 512)
    private String commentaires;

    @ManyToOne
    @JoinColumn(name="apprenti_id")
    private Apprenti apprenti;
    @ManyToOne
    @JoinColumn(name="entreprise_id")
    private Entreprise entreprise;
    @ManyToOne
    @JoinColumn(name="MA_id")
    private MaitreApprentissage maitre_apprentissage;


}
