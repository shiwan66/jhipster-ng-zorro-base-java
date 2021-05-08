package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalVacina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalVacina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalVacinaRepository extends JpaRepository<AnimalVacina, Long>, JpaSpecificationExecutor<AnimalVacina> {

}
