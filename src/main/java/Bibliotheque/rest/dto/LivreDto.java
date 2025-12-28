package Bibliotheque.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class LivreDto {

    private Long id;
    private String titre;
    private List<AuteurDto> auteurs;
    private LocalDateTime dateCreation;
    private BigDecimal prix;
    private String isbn;



// On passe par Lombok

//    public Long getId() { return id; }
//
//    public String getTitre(){
//        return titre;
//    }
//
//    public String getAuteur(){
//        return auteur;
//    }
//
//    public LocalDate getDateCreation(){ return dateCreation; }
//
//    public BigDecimal getPrix(){ return prix;}
//
//    public void SetId(Long id){
//        this.id=id;
//    }
//
//    public void SetTitre(String titre){
//        this.titre=titre;
//    }
//
//    public void SetAuteur(String auteur){
//        this.auteur=auteur;
//    }
//
//    public void SetDateCreation(LocalDate dateCreation){
//        this.dateCreation=dateCreation;
//    }
//
//    public void setPrix(BigDecimal prix) { this.prix = prix;}
//
//    //Constructeur
//    public LivreDto(long id, String titre, String auteur, LocalDate dateCreation, BigDecimal prix){
//        this.id=id;
//        this.titre=titre;
//        this.auteur=auteur;
//        this.dateCreation=dateCreation;
//        this.prix=prix;
//    }
}

