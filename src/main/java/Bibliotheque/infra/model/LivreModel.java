package Bibliotheque.infra.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="LIVRES")
public class LivreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titre", nullable = false)
    private String titre;
    @Column(name = "auteur", nullable = false)
    private String auteur;
    @Column(name="D_Creation")
    private LocalDateTime dateCreation;
    @Column(name="prix",precision = 10, scale = 2) //10 chiffres max, 2 aprés la virgule (Mieux pour la BdD)
    private BigDecimal prix;
}

