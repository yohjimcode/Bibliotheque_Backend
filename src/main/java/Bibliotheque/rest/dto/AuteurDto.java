package Bibliotheque.rest.dto;

import Bibliotheque.infra.model.LivreModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuteurDto {

    private int id;
    private String nom;
    private String prenom;
    private String biographie;

    private List<LivreModel> livres = new ArrayList<>();
}
