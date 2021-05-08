package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalTipoDeVacina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalTipoDeVacina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalTipoDeVacinaRepository extends JpaRepository<AnimalTipoDeVacina, Long>, JpaSpecificationExecutor<AnimalTipoDeVacina> {

}
