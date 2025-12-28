package Bibliotheque.infra.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="LIVRE")
public class LivreModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name="date_creation")
    private LocalDateTime dateCreation;

    @Column(name="prix",precision = 10, scale = 2) //10 chiffres max, 2 aprés la virgule (Mieux pour la BdD)
    private BigDecimal prix;

    @Column(name="isbn")
    private String isbn;

    @ManyToMany
    @JoinTable(
            name = "livre_auteur",
            joinColumns = @JoinColumn(name = "livre_id"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private List<AuteurModel> auteurs = new ArrayList<>();
}

