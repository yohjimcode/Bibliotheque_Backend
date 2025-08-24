package Bibliotheque.infra.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="UTILISATEUR")
public class UtilisateurModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "login", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
    @Column(name = "date_suppression")
    private LocalDateTime dateSuppresion;
    // ROLE

}
