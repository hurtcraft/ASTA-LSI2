package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Data
@Entity
@Table(name = "apprenti_archive")
public class ApprentiArchive {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archiveId;
    
    @Column(nullable = false)
    private String apprentiName;
    
    @Column(nullable = false)
    private String apprentiPrenom;
    
    @Column(nullable = false, unique = true)
    private String apprentiEmail;
    
    @Column(nullable = false)
    private String telephone;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private Year anneeAcademiqueDebut;
    
    @Column(nullable = false)
    private Year anneeAcademiqueFin;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Programme programmeFinal; // Programme au moment de l'archivage
    
    @ManyToOne
    @JoinColumn(name = "majeurId")
    private Majeur majeur;
    
    @ManyToOne
    @JoinColumn(name="entrepriseId")
    private Entreprise entreprise;
    
    @Column(nullable = false)
    private Year anneeArchivage; // Année où l'apprenti a été archivé
    
    // Constructeur par défaut
    public ApprentiArchive() {}
    
    // Constructeur pour créer un archive à partir d'un apprenti
    public ApprentiArchive(Apprenti apprenti, Year anneeArchivage) {
        this.apprentiName = apprenti.getApprentiName();
        this.apprentiPrenom = apprenti.getApprentiPrenom();
        this.apprentiEmail = apprenti.getApprentiEmail();
        this.telephone = apprenti.getTelephone();
        this.password = apprenti.getPassword();
        this.anneeAcademiqueDebut = apprenti.getAnneeAcademiqueDebut();
        this.anneeAcademiqueFin = apprenti.getAnneeAcademiqueFin();
        this.programmeFinal = apprenti.getProgramme();
        this.majeur = apprenti.getMajeur();
        this.entreprise = apprenti.getEntreprise();
        this.anneeArchivage = anneeArchivage;
    }
    
    public String getAnneeAcademiqueString() {
        return anneeAcademiqueDebut + "-" + anneeAcademiqueFin;
    }
    
    @Override
    public String toString() {
        return "ApprentiArchive{" +
                "archiveId=" + archiveId +
                ", apprentiName='" + apprentiName + '\'' +
                ", apprentiPrenom='" + apprentiPrenom + '\'' +
                ", apprentiEmail='" + apprentiEmail + '\'' +
                ", programmeFinal=" + programmeFinal +
                ", anneeArchivage=" + anneeArchivage +
                '}';
    }
}
