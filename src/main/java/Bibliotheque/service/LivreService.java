package Bibliotheque.service;

import Bibliotheque.infra.model.AuteurModel;
import Bibliotheque.infra.repo.AuteurJpaRepository;
import Bibliotheque.infra.repo.LivreJpaRepository;
import Bibliotheque.infra.model.LivreModel;
import Bibliotheque.rest.dto.AuteurDto;
import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.mapper.LivreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * transactionnal : si echec, rollback + meilleure perf
 */

@Service
public class LivreService {

    private final AuteurJpaRepository auteurJpaRepository;
    public final LivreJpaRepository livresJpaRepository;
    private final LivreApiService apiService;
    private final LivreMapper livreMapper;


    public LivreService(LivreJpaRepository livresJpaRepository, LivreMapper livreMapper, AuteurJpaRepository auteurJpaRepository, LivreApiService apiService) {
        this.livresJpaRepository = livresJpaRepository;
        this.livreMapper = livreMapper;
        this.auteurJpaRepository = auteurJpaRepository;
        this.apiService = apiService;
    }

    /**
     * @return stream() : Transforme la liste en flux de données
     * map(livreMapper::toDto) : Applique la conversion sur chaque élément
     * collect() : Reconstitue une liste
     */
    public List<LivreDto> getAllLivres() {
        List<LivreModel> livres = livresJpaRepository.findAll();
        return livres.stream().map(livreMapper::toDto).collect(Collectors.toList());
    }

    /**
     * @param id
     * @return .map(livreMapper : : toDto); // Convertit en DTO
     * Car le service doit toujours renvoyer des DtOs, jamais d'entity
     */
    public Optional<LivreDto> getLivre(long id) {
        return livresJpaRepository.findById(id).map(livreMapper::toDto);
    }

    /**
     * Crée un nouveau livre à partir d’un DTO.
     * - toModel() → convertit le DTO en entité LivreModel
     * - setDateCreation(...) → définit la date actuelle
     * - save(...) → insère dans la base
     * - toDto(...) → renvoie la version DTO du livre sauvegardé
     */
    public LivreDto createLivre(LivreDto livreDto) {
        LivreModel livre = livreMapper.toModel(livreDto);
        livre.setDateCreation(LocalDateTime.now());
        LivreModel savedLivre = livresJpaRepository.save(livre);
        return livreMapper.toDto(savedLivre);
    }

    /**
     * @param id
     * @param livreDto
     * @return décomposition :
     * cherche le livre en base
     * Si trouvé, met à jour les champs et sauvegarde
     * envoie au format DTO
     */
    public LivreDto updateLivre(Long id, LivreDto livreDto) {
        LivreModel livreModel = livresJpaRepository.findById(id).orElseThrow();
        livreModel.setTitre(livreDto.getTitre());
        livreModel.setPrix(livreDto.getPrix());
        if (livreDto.getAuteurs() != null && !livreDto.getAuteurs().isEmpty()) {
            // Récupérer ou créer chaque auteur
            List<AuteurModel> auteurs = livreDto.getAuteurs().stream()
                    .map(auteurDto -> {
                        if (auteurDto.getId() != null) {
                            // Auteur existant : récupérer depuis la base
                            return auteurJpaRepository.findById((long) auteurDto.getId())
                                    .orElseThrow(() -> new RuntimeException("Auteur non trouvé: " + auteurDto.getId()));
                        } else {
                            // Nouvel auteur : le créer
                            return ajouterAuteur(auteurDto);
                        }
                    })
                    .collect(Collectors.toList());

            livreModel.setAuteurs(auteurs);
        }

        return livreMapper.toDto(livresJpaRepository.save(livreModel));
    }

    private AuteurModel ajouterAuteur(AuteurDto auteurDto) {
        Optional<AuteurModel> existant = auteurJpaRepository
                .findByNomAuteur(auteurDto.getNomAuteur());

        if (existant.isPresent()) {
            return existant.get();
        } else {
            AuteurModel nouvelAuteur = new AuteurModel();
            nouvelAuteur.setNomAuteur(auteurDto.getNomAuteur());
            return auteurJpaRepository.save(nouvelAuteur);
        }
    }

    /**
     * @param id
     * @return renvoie true si suppr sinon false
     */
    public boolean deleteLivre(Long id) {
        if (livresJpaRepository.existsById(id)) {
            livresJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public LivreDto ajouterLivreDepuisIsbn(String isbn) {
        // 1. Récupérer les infos depuis Google Books API
        LivreDto livreDto = apiService.recupererInfosLivreParIsbn(isbn);

        if (livreDto == null) {
            throw new RuntimeException("Livre non trouvé pour l'ISBN: " + isbn);
        }

        // 2. Créer le livre
        LivreModel livre = new LivreModel();
        livre.setTitre(livreDto.getTitre());
        livre.setIsbn(livreDto.getIsbn());
        livre.setDateCreation(LocalDateTime.now());

        // 3. Gérer les auteurs (créer ou récupérer existants)
        if (livreDto.getAuteurs() != null) {
            List<AuteurModel> auteurs = livreDto.getAuteurs().stream()
                    .map(this::trouverOuCreerAuteur)
                    .collect(Collectors.toList());
            livre.setAuteurs(auteurs);
        }

        // 4. Sauvegarder
        LivreModel savedLivre = livresJpaRepository.save(livre);

        return livreMapper.toDto(savedLivre);
    }

    /**
     * Trouve un auteur existant ou le crée
     */
    private AuteurModel trouverOuCreerAuteur(AuteurDto auteurDto) {
        Optional<AuteurModel> existant = auteurJpaRepository
                .findByNomAuteur(auteurDto.getNomAuteur());

        if (existant.isPresent()) {
            return existant.get();
        } else {

            AuteurModel nouvelAuteur = new AuteurModel();
            nouvelAuteur.setNomAuteur(auteurDto.getNomAuteur());
            return auteurJpaRepository.save(nouvelAuteur);
        }
    }
}

