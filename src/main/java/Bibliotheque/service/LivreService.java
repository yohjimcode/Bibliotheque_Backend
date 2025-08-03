package Bibliotheque.service;

import Bibliotheque.infra.repo.LivreJpaRepository;
import Bibliotheque.infra.model.LivreModel;
import Bibliotheque.rest.dto.LivreDto;
import Bibliotheque.service.mapper.LivreMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * transactionnal : si echec, rollback + meilleure perf
 *
 */

@Service
@Transactional
public class LivreService {

    public final LivreJpaRepository livresJpaRepository;
    private final LivreMapper livreMapper;


    public LivreService(LivreJpaRepository livresJpaRepository, LivreMapper livreMapper) {
        this.livresJpaRepository = livresJpaRepository;
        this.livreMapper = livreMapper;
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

    public Optional<LivreDto> createLivre(LivreDto livreDto){
        LivreModel livre = livreMapper.toModel(livreDto);
        livre.setDateCreation(LocalDateTime.now());
        LivreModel savedLivre = livresJpaRepository.save(livre);
        return Optional.ofNullable(livreMapper.toDto(savedLivre));
    }

    /**
     *
     * @param id
     * @param livreDto
     * @return
     * décomposition :
     *  cherche le livre
     *  Si trouvé, met à jour les champs et sauvegarde
     *  converti et envoie
     */
    public Optional<LivreDto> updateLivre(Long id, LivreDto livreDto) {
        return livresJpaRepository.findById(id)           // 1. Cherche le livre
                .map(existingLivre -> {                  // 2. Si trouvé...
                    existingLivre.setTitre(livreDto.getTitre());    // 3. Met à jour
                    existingLivre.setAuteur(livreDto.getAuteur());
                    existingLivre.setPrix(livreDto.getPrix());
                    LivreModel savedLivre = livresJpaRepository.save(existingLivre); // 4. Sauvegarde
                    return livreMapper.toDto(savedLivre);    // 5. Convertit et renvoie
                });
        // Si pas trouvé, renvoie Optional.empty()
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

