package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @Column(nullable = false, length = 512)
    private String motCle;

    @Column(nullable = false, length = 100)
    private String metierCible;

    @Column(nullable = false, length = 512)
    private String commentaires;

    @ManyToOne
    @JoinColumn(name = "apprentiId")
    private Apprenti apprenti;

    @ManyToOne
    @JoinColumn(name = "entrepriseId")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "maId")
    private MaitreApprentissage maitreApprentissage;
}