package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rendu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rendu_id;

    @Column(nullable = false)
    private TypeRendu typeRendu;

    @Column(nullable = false,length = 256)
    private String theme;

    private double note_rendu;

    @Column(length = 512)
    private String commentaire;
}
