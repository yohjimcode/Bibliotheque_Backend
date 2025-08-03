package Bibliotheque.rest.controller;

import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.LivreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping (value = "/livres")
public class LivreController {

    private final LivreService livresService;

    public LivreController(LivreService livresService) {
        this.livresService = livresService;
    }

    @GetMapping("/id")
    public ResponseEntity<List<LivreDto>> getAllLivres(){
        List<LivreDto> livres = livresService.getAllLivres();
        return ResponseEntity.ok(livres);
    }
}
