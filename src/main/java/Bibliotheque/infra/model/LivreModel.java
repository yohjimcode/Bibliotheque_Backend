package Bibliotheque.infra.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY) // ne charge que les données nécessaires - mode paresseux
    @JoinColumn(name = "auteur_id") //colonne de jointure
    @JsonBackReference
    private AuteurModel auteur;
}

