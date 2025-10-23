package client.asta_lsi2.models;



import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import jakarta.persistence.*;
import lombok.Data;
import java.time.Year;

@Data
@Entity
public class Apprenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprentiId;

    @Column(nullable = false, length = 100)
    private String apprentiName;

    @Column(nullable = false, length = 100)
    private String apprentiPrenom;

    @Column(nullable = false, unique = true, length = 100)
    private String apprentiEmail;

    @Column(nullable = false)
    private Year apprentiYear;

    @ManyToOne
    @JoinColumn(name = "majeurId")
    private Majeur majeur;

    @ManyToOne
    @JoinColumn(name="entrepriseId")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "programmeId")
    private Programme programme;

    @Column(length = 20)
    private String telephone;

    @Column(nullable = false)
    private String password;

    public Apprenti(Long apprentiId, String apprentiName, String apprentiPrenom,
                    String apprentiEmail, String telephone,String password) {
        this.apprentiId = apprentiId;
        this.apprentiName = apprentiName;
        this.apprentiPrenom = apprentiPrenom;
        this.apprentiEmail = apprentiEmail;
        this.telephone = telephone;
        this.password = password;
    }

    public Apprenti() {
    }

    @Override
    public String toString() {
        return "Apprenti{" +
                "apprentiId=" + apprentiId +
                ", apprentiName='" + apprentiName + '\'' +
                ", apprentiPrenom='" + apprentiPrenom + '\'' +
                ", apprentiEmail='" + apprentiEmail + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
