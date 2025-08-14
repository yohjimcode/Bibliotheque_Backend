package Bibliotheque.infra.repo;

import Bibliotheque.infra.model.AuteurModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuteurJpaRepository extends JpaRepository<AuteurModel, Integer> {

}
