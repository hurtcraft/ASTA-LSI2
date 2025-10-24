package client.asta_lsi2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Data
@Entity
@Table(name = "annee_academique_courante")
public class AnneeAcademiqueCourante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private Year anneeDebut;
    
    @Column(nullable = false)
    private Year anneeFin;
    
    // Constructeur par défaut
    public AnneeAcademiqueCourante() {}
    
    // Constructeur avec année de début
    public AnneeAcademiqueCourante(Year anneeDebut) {
        this.anneeDebut = anneeDebut;
        this.anneeFin = anneeDebut.plusYears(1);
    }
    
    // Méthode pour obtenir l'année académique sous forme de string
    public String getAnneeAcademiqueString() {
        return anneeDebut + "-" + anneeFin;
    }
    
    // Méthode pour passer à l'année suivante
    public void passerAnneeSuivante() {
        this.anneeDebut = this.anneeDebut.plusYears(1);
        this.anneeFin = this.anneeFin.plusYears(1);
    }
    
    @Override
    public String toString() {
        return "AnneeAcademiqueCourante{" +
                "id=" + id +
                ", anneeDebut=" + anneeDebut +
                ", anneeFin=" + anneeFin +
                '}';
    }
}
