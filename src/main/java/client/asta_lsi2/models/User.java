package client.asta_lsi2.models;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles")
    private List<String> roles;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;



    public User(String email, String password, List<String> roles, String nom, String prenom) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User() {

    }
}
