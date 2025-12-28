package Bibliotheque.infra.repo;

import Bibliotheque.infra.model.AuteurModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuteurJpaRepository extends JpaRepository<AuteurModel, Long> {
    Optional<AuteurModel> findByNomAuteur(String nomAuteur);
}
