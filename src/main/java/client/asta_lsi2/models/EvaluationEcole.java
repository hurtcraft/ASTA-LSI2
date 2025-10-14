package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EvaluationEcole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluation_ecole_id;

    @OneToOne
    @JoinColumn(name = "rendu_id")
    private Rendu rendu;
    @OneToOne
    @JoinColumn(name = "soutenance_id")
    private Soutenance soutenance;

    @OneToOne
    @JoinColumn(name="apprenti_id")
    private Apprenti apprenti;

    @Column(length = 512)
    private String remarque;

    @Column(length = 512)
    private String feedback_enseignant;
}
