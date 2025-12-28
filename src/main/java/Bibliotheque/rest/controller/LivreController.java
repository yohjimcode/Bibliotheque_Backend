package Bibliotheque.rest.controller;

import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.LivreApiService;
import Bibliotheque.service.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping (value = "/api/livres")
public class LivreController {

    private final LivreService livresService;
    private final LivreApiService livreApiService;

    public LivreController(LivreService livresService, LivreApiService livreApiService) {
        this.livresService = livresService;
        this.livreApiService = livreApiService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LivreDto>> getAllLivres(){
        List<LivreDto> livres = livresService.getAllLivres();
        return ResponseEntity.ok(livres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivreDto>getLivre(@PathVariable Long id){
        Optional<LivreDto>livre = livresService.getLivre(id);
        if(livre.isPresent()){
            return ResponseEntity.ok(livre.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<LivreDto> createLivre(@RequestBody LivreDto livreDto){
        LivreDto livre = livresService.createLivre(livreDto);
        return ResponseEntity.created(URI.create("/livre" + livre.getId())).body(livre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivreDto>updateLivre(@PathVariable Long id, @RequestBody LivreDto livreDto){
        LivreDto updatedLivre = livresService.updateLivre(id,livreDto);
        return ResponseEntity.ok(updatedLivre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LivreDto> deletedLivre(@PathVariable Long id){
        boolean deleteLivre =  livresService.deleteLivre(id);
        if (deleteLivre) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/info/{isbn}")
    public ResponseEntity<LivreDto> getInfosLivre(@PathVariable String isbn) {
        LivreDto infos = livreApiService.recupererInfosLivreParIsbn(isbn);
        if (infos != null) {
            return ResponseEntity.ok(infos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/depuis-isbn")
    public ResponseEntity<LivreDto> ajouterLivreDepuisIsbn(@RequestParam String isbn) {
        LivreDto livre = livresService.ajouterLivreDepuisIsbn(isbn);
        return ResponseEntity.ok(livre);
    }
}
