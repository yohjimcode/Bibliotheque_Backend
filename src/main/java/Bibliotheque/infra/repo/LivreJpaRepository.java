package Bibliotheque.infra.repo;

import Bibliotheque.infra.model.LivreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LivreJpaRepository extends JpaRepository<LivreModel,Long> {


}

