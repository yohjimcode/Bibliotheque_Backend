package Bibliotheque.infra.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="AUTEUR")
public class AuteurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "biographie", columnDefinition = "TEXT")
    private String biographie;

    @OneToMany(mappedBy = "auteur")
    private List<LivreModel> livres = new ArrayList<>();

}
