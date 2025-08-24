package Bibliotheque.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UtilisateurDto {
    private long id;
    private String mail;
    private String username;
    private String password;
    private LocalDateTime dateCreation;
    private LocalDateTime dateSuppresion;
}
