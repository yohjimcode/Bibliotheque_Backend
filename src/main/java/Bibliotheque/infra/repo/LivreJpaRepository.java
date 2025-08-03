package Bibliotheque.infra.repo;

import Bibliotheque.infra.model.LivreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivreJpaRepository extends JpaRepository<LivreModel,Long> {

    Optional<LivreModel> findByTitre(String titre);

}

