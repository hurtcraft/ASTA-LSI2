package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visite_id;

    @Column(nullable = false)
    private Date visite_date;

    @Column(nullable = false)
    private FormatVisite format;

    @Column(length = 512)
    private String commentaires;
}
