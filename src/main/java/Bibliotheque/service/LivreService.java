package Bibliotheque.service;

import Bibliotheque.infra.model.AuteurModel;
import Bibliotheque.infra.repo.AuteurJpaRepository;
import Bibliotheque.infra.repo.LivreJpaRepository;
import Bibliotheque.infra.model.LivreModel;
import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.mapper.LivreMapper;
import org.springframework.stereotype.Service;


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
    private final LivreMapper livreMapper;



    public LivreService(LivreJpaRepository livresJpaRepository, LivreMapper livreMapper, AuteurJpaRepository auteurJpaRepository) {
        this.livresJpaRepository = livresJpaRepository;
        this.livreMapper = livreMapper;
        this.auteurJpaRepository = auteurJpaRepository;
    }

    /**
     * @return
     * stream() : Transforme la liste en flux de données
     * map(livreMapper::toDto) : Applique la conversion sur chaque élément
     * collect() : Reconstitue une liste
     */
    public List<LivreDto> getAllLivres() {
        List<LivreModel> livres = livresJpaRepository.findAll();
        return livres.stream().map(livreMapper::toDto).collect(Collectors.toList());
    }

    /**
     *
     * @param id
     * @return
     * .map(livreMapper::toDto); // Convertit en DTO
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
    public LivreDto createLivre(LivreDto livreDto){
        LivreModel livre = livreMapper.toModel(livreDto);
        livre.setDateCreation(LocalDateTime.now());
        LivreModel savedLivre = livresJpaRepository.save(livre);
        return livreMapper.toDto(savedLivre);
    }

    /**
     * @param id
     * @param livreDto
     * @return
     * décomposition :
     *  cherche le livre en base
     *  Si trouvé, met à jour les champs et sauvegarde
     *  envoie au format DTO
     */
    public LivreDto updateLivre(Long id, LivreDto livreDto) {
        LivreModel livreModel = livresJpaRepository.findById(id).orElseThrow();
        livreModel.setTitre(livreDto.getTitre());
        livreModel.setPrix(livreDto.getPrix());
        if(livreDto.getAuteur() != null) {
            AuteurModel auteurModel = auteurJpaRepository.findById(livreDto.getAuteur()
                    .getId()).orElseThrow(() -> new RuntimeException("Auteur non trouvé"));
            livreModel.setAuteur(auteurModel);
        }
        return livreMapper.toDto(livresJpaRepository.save(livreModel));
    }

    /**
     *
     * @param id
     * @return
     * renvoie true si suppr sinon false 
     */
    public boolean deleteLivre(Long id) {
        if (livresJpaRepository.existsById(id)) {
            livresJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

