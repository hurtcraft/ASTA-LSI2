package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Poste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long posteId;

    @Column(nullable = false, length = 100)
    private String label;


}
