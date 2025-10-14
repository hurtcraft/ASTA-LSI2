package client.asta_lsi2.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long soutenance_id;

    @Column(nullable = false)
    private Date date;

    private long note;
    @Column(length = 512)
    private String commentaires;



}
