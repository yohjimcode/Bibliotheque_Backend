package Bibliotheque.infra.repo;

import Bibliotheque.infra.model.UtilisateurModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UtilisateurModel,Long> {

    Optional<UtilisateurModel> findByUsername(String username);
}
