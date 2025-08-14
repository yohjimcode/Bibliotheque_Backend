package Bibliotheque.rest.controller;

import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping (value = "/livres")
public class LivreController {

    private final LivreService livresService;

    public LivreController(LivreService livresService) {
        this.livresService = livresService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LivreDto>> getAllLivres(){
        List<LivreDto> livres = livresService.getAllLivres();
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/id")
    public ResponseEntity<LivreDto>getLivre(@PathVariable Long id){
        Optional<LivreDto>livre = livresService.getLivre(id);
        if(livre.isPresent()){
            return ResponseEntity.ok(livre.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/created")
    public ResponseEntity<LivreDto> createLivre(@RequestBody LivreDto livreDto){
        LivreDto livre = livresService.createLivre(livreDto);
        return ResponseEntity.created(URI.create("/livres/created" + livre.getId())).body(livre);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LivreDto>updateLivre(@PathVariable Long id, @RequestBody LivreDto livreDto){
        LivreDto updatedLivre = livresService.updateLivre(id,livreDto);
        return ResponseEntity.ok(updatedLivre);
    }

    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<LivreDto> deletedLivre(@PathVariable Long id){
        boolean deleteLivre =  livresService.deleteLivre(id);
        if (deleteLivre) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
