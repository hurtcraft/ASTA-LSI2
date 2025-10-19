package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programmeId;

    @Column(nullable = false,unique = true, length = 100)
    private String label;

    @Override
    public String toString() {
        return "Programme{" +
                "programme_id=" + programmeId +
                ", label='" + label + '\'' +
                '}';
    }
}
