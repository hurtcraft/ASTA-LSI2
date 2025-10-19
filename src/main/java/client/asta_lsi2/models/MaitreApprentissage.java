package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MaitreApprentissage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maId;

    @Column(nullable = false, length = 100)
    private String maNom;

    @Column(nullable = false, length = 100)
    private String maPrenom;

    @Column(nullable = false, unique = true, length = 100)
    private String maEmail;

    @Column(length = 20)
    private String maTelephone;

    @Column(length = 1024)
    private String maRemarque;

    @ManyToOne
    @JoinColumn(name = "posteId")
    private Poste poste;

    public MaitreApprentissage() {
    }

    public MaitreApprentissage(Long maId, String maNom, String maPrenom,
                               String maEmail, String maTelephone, String maRemarque) {
        this.maId = maId;
        this.maNom = maNom;
        this.maPrenom = maPrenom;
        this.maEmail = maEmail;
        this.maTelephone = maTelephone;
        this.maRemarque = maRemarque;
    }

    @Override
    public String toString() {
        return "MaitreApprentissage{" +
                "maId=" + maId +
                ", maNom='" + maNom + '\'' +
                ", maPrenom='" + maPrenom + '\'' +
                ", maEmail='" + maEmail + '\'' +
                ", maTelephone='" + maTelephone + '\'' +
                ", maRemarque='" + maRemarque + '\'' +
                '}';
    }
}