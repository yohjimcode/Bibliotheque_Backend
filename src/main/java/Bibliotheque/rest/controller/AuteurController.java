package Bibliotheque.rest.controller;

import Bibliotheque.rest.dto.AuteurDto;
import Bibliotheque.service.AuteurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/auteurs")
public class AuteurController {

    private final AuteurService auteurService;


    public AuteurController(AuteurService auteurService) {
        this.auteurService = auteurService;
    }

    // map à la place du otpional
    @GetMapping("/{id}")
    public ResponseEntity<AuteurDto> getAuteur(@PathVariable int id) {
        return auteurService.getAuteur(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuteurDto>>getAllAuteurs(){
        List<AuteurDto> auteurs = auteurService.getAllLivres();
        return ResponseEntity.ok(auteurs);
    }

    @PostMapping("/created")
    public ResponseEntity<AuteurDto> createAuteur(@RequestBody AuteurDto auteurDto){
       AuteurDto auteur = auteurService.createAuteur(auteurDto);
       return ResponseEntity.created(URI.create("/auteurs/" + auteur.getId())).body(auteur);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AuteurDto>updateAuteur(@PathVariable int id, @RequestBody AuteurDto auteurDto){
        AuteurDto updatedAuteur = auteurService.updateAuteur(id,auteurDto);
        return ResponseEntity.ok(updatedAuteur);
    }

    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<AuteurDto> deleteAuteur(@PathVariable int id){
        boolean deletedAuteur =  auteurService.deletedAuteur(id);
        if (deletedAuteur) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
