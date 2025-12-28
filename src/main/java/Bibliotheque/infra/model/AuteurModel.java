package Bibliotheque.infra.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;
    @Column(name = "nom", nullable = false)
    private String nomAuteur;

    @ManyToMany(mappedBy = "auteurs")
    private List<LivreModel> livres = new ArrayList<>();

}
