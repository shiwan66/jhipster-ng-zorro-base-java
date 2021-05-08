package br.com.ngzorro.repository;
import br.com.ngzorro.domain.AnimalCio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalCio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalCioRepository extends JpaRepository<AnimalCio, Long>, JpaSpecificationExecutor<AnimalCio> {

}
