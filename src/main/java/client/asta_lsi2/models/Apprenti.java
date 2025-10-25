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
    private Year anneeAcademiqueDebut;

    @Column(nullable = false)
    private Year anneeAcademiqueFin;

    @ManyToOne
    @JoinColumn(name = "majeurId")
    private Majeur majeur;

    @ManyToOne
    @JoinColumn(name="entrepriseId")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "maId")
    private MaitreApprentissage maitreApprentissage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Programme programme;

    @Column(length = 20)
    private String telephone;

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

    // Méthode pour définir automatiquement l'année académique courante
    public void setAnneeAcademiqueCourante() {
        Year currentYear = Year.now();
        this.anneeAcademiqueDebut = currentYear;
        this.anneeAcademiqueFin = currentYear.plusYears(1);
    }

    // Méthode pour obtenir l'année académique sous forme de chaîne
    public String getAnneeAcademiqueString() {
        return anneeAcademiqueDebut + "-" + anneeAcademiqueFin;
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
