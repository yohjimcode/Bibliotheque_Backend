package Bibliotheque.rest.controller;

import Bibliotheque.rest.dto.UtilisateurDto;
import Bibliotheque.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("/register")
    public ResponseEntity<UtilisateurDto> register(@RequestBody UtilisateurDto utilisateurDto){
        UtilisateurDto user = utilisateurService.register(utilisateurDto);
        return ResponseEntity.ok(user);
    }

}
